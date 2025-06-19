package ism.absence.web.controller.impl;

import ism.absence.core.dto.response.RestResponse;
import ism.absence.data.models.Dette;
import ism.absence.data.models.Paiement;
import ism.absence.data.repository.DetteRepository;
import ism.absence.data.repository.PaiementRepository;
import ism.absence.web.controller.PaiementController;
import ism.absence.web.dto.request.PaiementRequest;
import ism.absence.web.dto.response.PaiementResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
public class PaiementControllerImpl implements PaiementController {
    private final PaiementRepository paiementRepository;
    private final DetteRepository detteRepository;
    @Override
    public ResponseEntity<?> createPaiement(PaiementRequest paiementRequest) {
        Dette dette = detteRepository.findById(paiementRequest.getDetteId()).get();

        if (paiementRequest.getMontant() > dette.getMontantRestant()) {
            return new ResponseEntity<>(RestResponse.responseError2(
                    String.format("Le montant ne peut pas dépasser %s",dette.getMontantRestant())
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
        PaiementResponseDTO  paiementResponseDTO = new PaiementResponseDTO();
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
}