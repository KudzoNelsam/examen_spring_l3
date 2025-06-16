package ism.absence.core.controllers.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import ism.absence.config.JwtUtil;
import ism.absence.core.controllers.AuthController;
import ism.absence.core.dto.request.UserAuthRequestDTO;
import ism.absence.core.dto.request.UserRequestDTO;
import ism.absence.core.dto.response.RestResponse;
import ism.absence.core.dto.response.UserResponseDTO;
import ism.absence.data.enums.StatutPointage;
import ism.absence.data.enums.UserRole;
import ism.absence.data.models.*;
import ism.absence.data.records.Localisation;
import ism.absence.data.repository.*;
import ism.absence.mobile.dto.response.EtudiantResponseDTO;
import ism.absence.mobile.dto.response.SeanceCoursDTO;
import ism.absence.services.EtudiantService;
import ism.absence.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Tag(name = "User Management", description = "APIs for managing users")

@RequiredArgsConstructor
@RestController
public class AuthControllerImpl implements AuthController {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final EtudiantService etudiantService;
    private final SeanceCoursRepository seanceCoursRepository;
    private final SalleRepository salleRepository;
    private final InscriptionRepository inscriptionRepository;
    private final PointageRepository pointageRepository;
    private final ClasseRepository classeRepository;
    private final CoursRepository coursRepository;
    private final ModuleRepository moduleRepository;

    @Override
    public ResponseEntity<?> getInformation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    RestResponse.response(HttpStatus.UNAUTHORIZED, null, "unauthorized")
            );
        }

        User user = (User) authentication.getPrincipal();
        System.out.println("Utilisateur connecté : " + user.getFullName());

        Etudiant etudiant = etudiantService.findByUserId(user.getId());
        if (etudiant == null) {
            System.out.println("Aucun étudiant trouvé pour l'utilisateur " + user.getId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    RestResponse.response(HttpStatus.NOT_FOUND, null, "Étudiant non trouvé")
            );
        }

        Inscription inscription = inscriptionRepository.findByEtudiantId(etudiant.getId());
        if (inscription == null) {
            System.out.println("Pas d'inscription trouvée pour l'étudiant " + etudiant.getMatricule());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    RestResponse.response(HttpStatus.NOT_FOUND, null, "Inscription non trouvée")
            );
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();

        List<Pointage> pointages = pointageRepository.findByMatricule(etudiant.getMatricule());

        if (pointages.isEmpty()) {
            System.out.println("L'étudiant " + etudiant.getMatricule() + " n'a pas de pointage.");
        } else {
            System.out.println("Pointages trouvés : " + pointages.size());
        }

        Pointage pointage = pointages.stream()
                .filter(p -> {
                    String seanceId = p.getSeanceCoursId();
                    if (seanceId == null) {
                        System.out.println("⚠️ Pointage sans séance ID : " + p);
                        return false;
                    }
                    Optional<SeanceCours> opt = seanceCoursRepository.findById(seanceId);
                    return opt.isPresent() && opt.get().getDate().isEqual(today);
                })
                .min(Comparator.comparingLong(p -> {
                    Optional<SeanceCours> opt = seanceCoursRepository.findById(p.getSeanceCoursId());
                    if (opt.isEmpty()) {
                        return Long.MAX_VALUE;
                    }
                    SeanceCours sc = opt.get();
                    LocalDateTime debut = LocalDateTime.of(sc.getDate(), sc.getHeureDebut());
                    return Math.abs(Duration.between(now, debut).toMillis());
                }))
                .orElse(null);

        if (pointage == null) {
            System.out.println("❌ Aucun pointage valide trouvé pour aujourd’hui.");
        }

        SeanceCours seanceCours = null;
        if (pointage != null && pointage.getSeanceCoursId() != null) {
            seanceCours = seanceCoursRepository.findById(pointage.getSeanceCoursId()).orElse(null);
        }

        Cours cours = null;
        Salle salle = null;

        if (seanceCours != null) {
            String coursId = seanceCours.getCoursId();
            String salleId = seanceCours.getSalleId();

            if (coursId != null) {
                cours = coursRepository.findById(coursId).orElse(null);
            } else {
                System.out.println("⚠️ coursId null dans seanceCours : " + seanceCours.getId());
            }

            if (salleId != null) {
                salle = salleRepository.findById(salleId).orElse(null);
            } else {
                System.out.println("⚠️ salleId null dans seanceCours : " + seanceCours.getId());
            }
        }

        EtudiantResponseDTO dto = new EtudiantResponseDTO();
        dto.setFullName(user.getFullName());
        dto.setMatricule(etudiant.getMatricule());
        dto.setPhotoUrl(user.getPhotoUrl()); // ça va venir de cloudinary

        SeanceCoursDTO seanceCoursDTO = getSeanceCoursDTO(cours, seanceCours, salle, pointage);
        dto.setCurrentSeanceCours(seanceCoursDTO);

        boolean estValide = inscription.isActive();
        String passer = estValide ? "passer" : "invalide"; // opérateur ternaire

        String qrCodeContent = passer;

        if (pointage != null && pointage.getSeanceCoursId() != null && pointage.getVigileId() != null) {
            qrCodeContent += "|" + etudiant.getMatricule() + "|" + pointage.getVigileId() + "|" + pointage.getSeanceCoursId();
        } else {
            System.out.println("⚠️ Impossible de générer le QR code avec seance : pointage ou ses champs sont null");
        }


        dto.setAJour(estValide);
        dto.setMotCle(passer);
        dto.setQrCodeContent(qrCodeContent);

        return ResponseEntity.ok(
                RestResponse.response(
                        HttpStatus.OK,
                        dto,
                        EtudiantResponseDTO.class.getSimpleName()
                )
        );
    }


    private static SeanceCoursDTO getSeanceCoursDTO(Cours cours, SeanceCours seanceCours, Salle salle, Pointage pointage) {
        SeanceCoursDTO seanceCoursDTO = new SeanceCoursDTO();

        seanceCoursDTO.setProfesseur(cours == null ? "" : cours.getProfesseur());
        seanceCoursDTO.setNomCours(cours == null ? "" : cours.getNom());
        seanceCoursDTO.setHeureDebut(seanceCours == null ? LocalTime.of(0, 0, 0) : seanceCours.getHeureDebut());
        seanceCoursDTO.setHeureFin(seanceCours == null ? LocalTime.of(0, 0, 0) : seanceCours.getHeureFin());
        seanceCoursDTO.setLocalisation(salle == null ? new Localisation(0, 0) : salle.getLocalisation());

        if (pointage != null && pointage.getStatut() != null) {
            seanceCoursDTO.setStatutPointage(pointage.getStatut());
        } else {
            // Par exemple, tu peux définir un statut par défaut, comme "INCONNU" ou null selon ton Enum
            seanceCoursDTO.setStatutPointage(StatutPointage.INCONNU);
        }

        return seanceCoursDTO;
    }

    @Override
    public ResponseEntity<?> register(@Validated @RequestBody UserRequestDTO userRequestDto) {
        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(userRequestDto.getPassword());
        user.setFullName(userRequestDto.getFullName());
        user.setAdresse(userRequestDto.getAdresse());
        user.setLocalisation(userRequestDto.getLocalisation());
        user.setRole(UserRole.valueOf(userRequestDto.getRole()));
        user = userService.save(user);
        String token = jwtUtil.generateToken(user.getUsername());
        UserResponseDTO authResponse = UserResponseDTO.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestResponse.response(HttpStatus.CREATED, authResponse, "user_registered")
        );
    }


    @Override
    public ResponseEntity<?> login(@Validated @RequestBody UserAuthRequestDTO request) {
        Optional<User> userOpt = userService.login(request.getUsername(), request.getPassword());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    RestResponse.response(HttpStatus.UNAUTHORIZED, null, "invalid_credentials")
            );
        }
        User user = userOpt.get();
        String token = jwtUtil.generateToken(user.getUsername());
        UserResponseDTO authResponse = UserResponseDTO.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
        return ResponseEntity.ok(
                RestResponse.response(HttpStatus.OK, authResponse, "login_success")
        );
    }
}