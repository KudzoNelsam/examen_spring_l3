package ism.absence.web.controller.impl;

import ism.absence.core.dto.response.RestResponse;
import ism.absence.data.models.Client;
import ism.absence.data.models.Dette;
import ism.absence.data.models.Paiement;
import ism.absence.data.repository.ClientRepository;
import ism.absence.data.repository.DetteRepository;
import ism.absence.data.repository.PaiementRepository;
import ism.absence.web.controller.PaiementController;
import ism.absence.web.dto.request.PaiementRequest;
import ism.absence.web.dto.response.ClientWithDetteResponse;
import ism.absence.web.dto.response.PaiementResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class PaiementControllerImpl implements PaiementController {
    private final PaiementRepository paiementRepository;
    private final DetteRepository detteRepository;
    private final ClientRepository clientRepository;

    @Override
    public ResponseEntity<?> createPaiement(PaiementRequest paiementRequest) {
        Dette dette = detteRepository.findById(paiementRequest.getDetteId()).get();

        if (paiementRequest.getMontant() > dette.getMontantRestant()) {
            return new ResponseEntity<>(RestResponse.responseError2(
                    String.format("Le montant ne peut pas dépasser %s", dette.getMontantRestant())
            ), HttpStatus.BAD_REQUEST);
        }
        dette.setMontantPaye(dette.getMontantPaye() + paiementRequest.getMontant());
        dette.setMontantRestant(dette.getMontantDette() - dette.getMontantPaye());
        dette = detteRepository.save(dette);
        Paiement paiement = new Paiement();
        paiement.setDate(LocalDate.now());
        paiement.setClientId(paiementRequest.getClientId());
        paiement.setDetteId(dette.getId());
        paiement.setMontant(paiementRequest.getMontant());
        paiement = paiementRepository.save(paiement);
        PaiementResponseDTO paiementResponseDTO = new PaiementResponseDTO();
        paiementResponseDTO.setDetteId(dette.getId());
        paiementResponseDTO.setLocalDate(paiement.getDate());
        paiementResponseDTO.setMontant(paiement.getMontant());
        paiementResponseDTO.setMontantRestant(dette.getMontantRestant());
        return new ResponseEntity<>(RestResponse.response(
                HttpStatus.CREATED,
                paiementResponseDTO,
                dette.getMontantRestant() == 0 ? "Dette remboursé" : "Payement Effectué avec succès"

        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getPaiementsByFiltrer(String telephone, String numeroDette, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            // Validate and normalize inputs
            boolean hasTelephone = telephone != null && !telephone.trim().isEmpty();
            boolean hasNumeroDette = numeroDette != null && !numeroDette.trim().isEmpty();

            FilterResult filterResult = getFilteredPaiements(telephone, numeroDette, hasTelephone, hasNumeroDette, pageable);

            List<PaiementResponseDTO> result = mapToResponseDTOs(filterResult.paiementPage.getContent());

            Map<String, Object> data = buildResponseData(result, telephone, numeroDette, filterResult.client, filterResult.dette);

            return ResponseEntity.ok(RestResponse.responsePaginate(
                    HttpStatus.OK,
                    data,
                    filterResult.paiementPage.getNumber(),
                    filterResult.paiementPage.getTotalPages(),
                    filterResult.paiementPage.getTotalElements(),
                    filterResult.paiementPage.hasPrevious(),
                    filterResult.paiementPage.hasNext(),
                    PaiementResponseDTO.class.getTypeName()
            ));

        } catch (Exception e) {
            // Log error and return appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(RestResponse.responseError2("Error filtering payments: " + e.getMessage()));
        }
    }

    private FilterResult getFilteredPaiements(String telephone, String numeroDette,
                                              boolean hasTelephone, boolean hasNumeroDette,
                                              Pageable pageable) {
        Page<Paiement> paiementPage;
        Client client = null;
        Dette dette = null;

        if (!hasTelephone && !hasNumeroDette) {
            paiementPage = paiementRepository.findAll(pageable);
        } else if (hasTelephone && hasNumeroDette) {
            client = clientRepository.findByTelephone(telephone.trim());
            dette = detteRepository.findByNumero(numeroDette.trim());

            if (client == null || dette == null) {
                paiementPage = Page.empty(pageable);
            } else {
                paiementPage = paiementRepository.findByDetteIdAndClientId(
                        dette.getId(), client.getId(), pageable);
            }
        } else {
            // Handle single parameter cases
            // Add your specific business logic here
            paiementPage = Page.empty(pageable);
        }

        return new FilterResult(paiementPage, client, dette);
    }

    private List<PaiementResponseDTO> mapToResponseDTOs(List<Paiement> paiements) {
        return paiements.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private PaiementResponseDTO mapToResponseDTO(Paiement paiement) {
        PaiementResponseDTO dto = new PaiementResponseDTO();
        dto.setDetteId(paiement.getId()); // Fixed mapping
        dto.setLocalDate(paiement.getDate());
        dto.setMontant(paiement.getMontant());
        dto.setMontantRestant(detteRepository.findById(paiement.getDetteId()).get().getMontantRestant()); // Get from payment's debt
        return dto;
    }

    private Map<String, Object> buildResponseData(List<PaiementResponseDTO> result, String telephone,
                                                  String numeroDette, Client client, Dette dette) {
        Map<String, Object> data = new HashMap<>();
        data.put("result", result);
        data.put("numeroDette", numeroDette);
        data.put("telephone", telephone);
        data.put("clientId", client != null ? client.getId() : null);
        data.put("detteId", dette != null ? dette.getId() : null);
        data.put("clientNom", client != null ? client.getNom() : "");
        return data;
    }

    private static class FilterResult {
        final Page<Paiement> paiementPage;
        final Client client;
        final Dette dette;

        FilterResult(Page<Paiement> paiementPage, Client client, Dette dette) {
            this.paiementPage = paiementPage;
            this.client = client;
            this.dette = dette;
        }
    }
}