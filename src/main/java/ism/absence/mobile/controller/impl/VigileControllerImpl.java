package ism.absence.mobile.controller.impl;

import ism.absence.core.dto.response.RestResponse;
import ism.absence.data.enums.StatutPointage;
import ism.absence.data.models.Etudiant;
import ism.absence.data.models.Inscription;
import ism.absence.data.models.Pointage;
import ism.absence.data.models.SeanceCours;
import ism.absence.data.repository.EtudiantRepository;
import ism.absence.data.repository.InscriptionRepository;
import ism.absence.data.repository.PointageRepository;
import ism.absence.data.repository.SeanceCoursRepository;
import ism.absence.mobile.controller.VigileController;
import ism.absence.mobile.dto.request.PointageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class VigileControllerImpl implements VigileController {
    private final PointageRepository pointageRepository;
    private final InscriptionRepository inscriptionRepository;
    private final EtudiantRepository etudiantRepository;
    private final SeanceCoursRepository seanceCoursRepository;

    @Override
    public ResponseEntity<?> pointer(PointageRequest pointageRequest) {
        String matricule = pointageRequest.getMatricule();
        String seanceCoursId = pointageRequest.getSeanceCoursId();
        String vigileId = pointageRequest.getVigileId();

        // Vérifie l'existence de l'étudiant
        Optional<Etudiant> optEtudiant = etudiantRepository.findByMatricule(matricule);
        if (optEtudiant.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(RestResponse.response(HttpStatus.NOT_FOUND, null, "Étudiant introuvable"));
        }
        Etudiant etudiant = optEtudiant.get();

        // Vérifie l'inscription active
        Inscription inscription = inscriptionRepository.findByEtudiantId(etudiant.getId());
        if (inscription == null || !inscription.isActive()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(RestResponse.response(HttpStatus.BAD_REQUEST, null, "Inscription non active"));
        }

        // Vérifie l'existence de la séance
        Optional<SeanceCours> optSeance = seanceCoursRepository.findById(seanceCoursId);
        if (optSeance.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(RestResponse.response(HttpStatus.NOT_FOUND, null, "Séance introuvable"));
        }
        SeanceCours seance = optSeance.get();

        LocalDateTime now = LocalDateTime.now();

        // Vérifie s'il existe déjà un pointage EN_ATTENTE pour cet étudiant et cette séance
        Pointage pointage = pointageRepository.findByMatriculeAndSeanceCoursIdAndStatut(
                matricule, seanceCoursId, StatutPointage.EN_ATTENTE);

        if (pointage == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(RestResponse.response(HttpStatus.CONFLICT, null, "Déjà pointé"));
        }

        // Met à jour le statut et la date du pointage
        pointage.setStatut(StatutPointage.PRESENT);
        pointage.setDate(now);
        pointageRepository.save(pointage);

        System.out.println(pointage);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestResponse.response(HttpStatus.OK, true, "pointage"));
    }

}