package ism.absence.mobile.controller.impl;

import ism.absence.core.dto.response.RestResponse;
import ism.absence.data.enums.StatutPointage;
import ism.absence.data.enums.UserRole;
import ism.absence.data.models.Cours;
import ism.absence.data.models.Etudiant;
import ism.absence.data.models.Pointage;
import ism.absence.data.models.User;
import ism.absence.data.repository.CoursRepository;
import ism.absence.data.repository.PointageRepository;
import ism.absence.data.repository.SeanceCoursRepository;
import ism.absence.mobile.controller.EtudiantController;
import ism.absence.mobile.dto.request.EtudiantRequestDTO;
import ism.absence.mobile.dto.response.AbsenceRetardDTO;
import ism.absence.mobile.dto.response.EtudiantResponseDTO;
import ism.absence.services.EtudiantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Transactional
public class EtudiantControllerImpl implements EtudiantController {
    private final EtudiantService etudiantService;
    private final PointageRepository pointageRepository;
    private final CoursRepository coursRepository;
    private final SeanceCoursRepository seanceCoursRepository;

    @Override
    public ResponseEntity<?> getPointages(String matricule, Principal principal) {

        List<Pointage> pointages = pointageRepository.findByMatricule(matricule);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<AbsenceRetardDTO> dtos = pointages.stream()
                .filter(p -> p.getStatut() == StatutPointage.ABSENT || p.getStatut() == StatutPointage.EN_RETARD)
                .map(p -> {
                    Cours cours = coursRepository.findById(
                            seanceCoursRepository.findById(p.getSeanceCoursId()).get().getCoursId()
                    ).orElse(null);
                    AbsenceRetardDTO dto = new AbsenceRetardDTO();
                    dto.setId(p.getId());
                    dto.setDate(p.getDate().format(formatter));
                    dto.setType(p.getStatut() == StatutPointage.ABSENT ? "ABSENCE" : "RETARD");
                    dto.setJustifie(p.isEstJustifie());
                    dto.setNomCours(cours == null ? "" : cours.getNom());
                    dto.setNomProf(cours == null ? "" : cours.getProfesseur());
                    dto.setSeanceCoursId(p.getSeanceCoursId());
                    return dto;
                })
                .toList();
        System.out.println(pointages);

        return ResponseEntity.ok(
                RestResponse.response(HttpStatus.OK, dtos, AbsenceRetardDTO.class.getTypeName())
        );
    }

    @Override
    public ResponseEntity<?> getJustificatifs(String matricule) {
        return null;
    }

    @Override
    public ResponseEntity<?> save(EtudiantRequestDTO dto) {
        Etudiant etudiant = new Etudiant();
        User user = new User();
        user.setRole(UserRole.valueOf(dto.getUserRequest().getRole()));
        user.setPassword(dto.getUserRequest().getPassword());
        user.setAdresse(dto.getUserRequest().getAdresse());
        user.setLocalisation(dto.getUserRequest().getLocalisation());
        user.setFullName(dto.getUserRequest().getFullName());
        user.setUsername(dto.getUserRequest().getUsername());
        etudiant.setUserId(user.getId());
        etudiant = etudiantService.save(etudiant);
        return new ResponseEntity<>(
                RestResponse.response(
                        HttpStatus.CREATED,
                        "",
                        "Etudiant_Create"
                ),
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<?> getByMatricule(String matricule) {
        Etudiant etudiant = etudiantService.findByMatricule(matricule);
        EtudiantResponseDTO dto = new EtudiantResponseDTO();
        return new ResponseEntity<>(
                RestResponse.response(
                        HttpStatus.OK,
                        dto,
                        "Etudiant_Found"
                ),
                HttpStatus.OK
        );
    }
}