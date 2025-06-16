package ism.absence.mobile.controller.impl;

import ism.absence.core.dto.response.RestResponse;
import ism.absence.data.enums.EtatJustificatif;
import ism.absence.data.models.Justificatif;
import ism.absence.data.repository.JustificatifRepository;
import ism.absence.mobile.controller.AdminController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {

    private final JustificatifRepository justificatifRepository;

    @Override
    public ResponseEntity<?> validerJustificatif(String id) {
        Optional<Justificatif> justificatif = justificatifRepository.findById(id);
        if (justificatif.isPresent()) {
            Justificatif j = justificatif.get();
            j.setEtat(EtatJustificatif.VALIDE);
            justificatifRepository.save(j);
            return ResponseEntity.ok(RestResponse.response(HttpStatus.OK, j, "Justificatif validé"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(RestResponse.response(HttpStatus.NOT_FOUND, null, "Justificatif non trouvé"));
        }
    }

    @Override
    public ResponseEntity<?> refuserJustificatif(String id) {
        Optional<Justificatif> justificatif = justificatifRepository.findById(id);
        if (justificatif.isPresent()) {
            Justificatif j = justificatif.get();
            j.setEtat(EtatJustificatif.REJETE);
            justificatifRepository.save(j);
            return ResponseEntity.ok(RestResponse.response(HttpStatus.OK, j, "Justificatif refusé"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(RestResponse.response(HttpStatus.NOT_FOUND, null, "Justificatif non trouvé"));
        }
    }

    @Override
    public ResponseEntity<?> getJustificatifsEnAttente() {
        List<Justificatif> justificatifs = justificatifRepository.findAllByEtat(EtatJustificatif.EN_COURS);
        return ResponseEntity.ok(RestResponse.response(HttpStatus.OK, justificatifs, "Liste des justificatifs en attente"));
    }
}