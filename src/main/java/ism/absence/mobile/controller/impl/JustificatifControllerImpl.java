package ism.absence.mobile.controller.impl;

import ism.absence.core.dto.response.RestResponse;
import ism.absence.data.enums.EtatJustificatif;
import ism.absence.data.models.Justificatif;
import ism.absence.data.repository.JustificatifRepository;
import ism.absence.mobile.controller.JustificatifController;
import ism.absence.mobile.dto.request.JustificatifRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class JustificatifControllerImpl implements JustificatifController {

    private final JustificatifRepository justificatifRepository;

    @Override
    public ResponseEntity<?> submit(JustificatifRequestDTO dto) {
        Justificatif justificatif = new Justificatif();
        justificatif.setMotif(dto.getMotif());
        justificatif.setPiecesJointes(dto.getPiecesJointes()); // List<String> d'URLs Cloudinary
        justificatif.setDate(LocalDateTime.now());
        justificatif.setEtat(EtatJustificatif.EN_COURS);

        justificatif = justificatifRepository.save(justificatif);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestResponse.response(HttpStatus.CREATED, justificatif, "Justificatif enregistré"));
    }

    @Override
    public ResponseEntity<?> getByEtudiant(String matricule) {
        List<Justificatif> justificatifs = justificatifRepository.findAllByMatricule(matricule);
        return ResponseEntity.ok(
                RestResponse.response(HttpStatus.OK, justificatifs, "Liste des justificatifs de l'étudiant"));
    }

    @Override
    public ResponseEntity<?> getById(String id) {
        Optional<Justificatif> justificatif = justificatifRepository.findById(id);
        return justificatif.map(value -> ResponseEntity.ok(
                RestResponse.response(HttpStatus.OK, value, "Justificatif trouvé"))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(RestResponse.response(HttpStatus.NOT_FOUND, null, "Justificatif non trouvé")));
    }
}