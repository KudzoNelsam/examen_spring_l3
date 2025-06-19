package ism.absence.web.controller.impl;

import ism.absence.core.dto.response.RestResponse;
import ism.absence.data.models.Dette;
import ism.absence.data.models.Paiement;
import ism.absence.data.repository.ClientRepository;
import ism.absence.data.repository.DetteRepository;
import ism.absence.data.repository.PaiementRepository;
import ism.absence.web.controller.DetteController;
import ism.absence.web.dto.request.ClientRequest;
import ism.absence.web.dto.request.DetteRequest;
import ism.absence.web.dto.response.ClientWithDetteResponse;
import ism.absence.web.dto.response.DetteResponseDTO;
import ism.absence.web.dto.response.DetteWithPaiementsResponseDTO;
import ism.absence.web.dto.response.PaiementResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class DetteControllerImpl implements DetteController {
    private final DetteRepository detteRepository;
    private final ClientRepository clientRepository;
    private final PaiementRepository paiementRepository;

    @Override
    public ResponseEntity<?> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Dette> pageClient = detteRepository.findAll(pageable);
        List<DetteResponseDTO> data = new ArrayList<>();
        for (Dette dette : pageClient.getContent()) {
            DetteResponseDTO detteResponseDTO = new DetteResponseDTO();
            detteResponseDTO.setMontantDette(dette.getMontantDette());
            detteResponseDTO.setNumero(dette.getNumero());
            detteResponseDTO.setDate(dette.getDate());
            detteResponseDTO.setNumero(dette.getNumero());
            detteResponseDTO.setMontantRestant(dette.getMontantRestant());
            detteResponseDTO.setMontantPaye(dette.getMontantPaye());
            data.add(detteResponseDTO);
        }

        return new ResponseEntity<>(RestResponse.responsePaginate(
                HttpStatus.OK,
                data,
                pageClient.getNumber(),
                pageClient.getTotalPages(),
                pageClient.getTotalElements(),
                pageClient.hasPrevious(),
                pageClient.hasNext(),
                DetteResponseDTO.class.getTypeName()
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findWithPaiements(String id, int page, int size) {
        System.out.println(id);
        Pageable pageable = PageRequest.of(page, size);
        Dette dette = detteRepository.findById(id).orElse(null);
        DetteResponseDTO detteResponseDTO = new DetteResponseDTO();
        detteResponseDTO.setMontantDette(dette.getMontantDette());
        detteResponseDTO.setNumero(dette.getNumero());
        detteResponseDTO.setDate(dette.getDate());
        detteResponseDTO.setNumero(dette.getNumero());
        detteResponseDTO.setMontantPaye(dette.getMontantPaye());
        detteResponseDTO.setMontantRestant(dette.getMontantRestant());
        detteResponseDTO.setMontantDette(dette.getMontantDette());

        Page<Paiement> paiementPage = paiementRepository.findByDetteId(id, pageable);
        List<PaiementResponseDTO> data = new ArrayList<>();
        for (Paiement paiement : paiementPage.getContent()) {
            PaiementResponseDTO paiementResponseDTO = new PaiementResponseDTO();
            paiementResponseDTO.setDetteId(paiement.getDetteId());
            paiementResponseDTO.setMontant(paiement.getMontant());
            paiementResponseDTO.setLocalDate(paiement.getDate());
            paiementResponseDTO.setMontantRestant(dette.getMontantRestant());
            data.add(paiementResponseDTO);
        }
        DetteWithPaiementsResponseDTO result = new DetteWithPaiementsResponseDTO();
        result.setDetteResponseDTO(
                detteResponseDTO
        );
        result.setPaiementResponseDTOList(data);
        return new ResponseEntity<>(RestResponse.responsePaginate(
                HttpStatus.OK,
                result,
                paiementPage.getNumber(),
                paiementPage.getTotalPages(),
                paiementPage.getTotalElements(),
                paiementPage.hasPrevious(),
                paiementPage.hasNext(),
                DetteWithPaiementsResponseDTO.class.getTypeName()
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> save(DetteRequest detteRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> findById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> update(ClientRequest client, String id) {
        return null;
    }
}