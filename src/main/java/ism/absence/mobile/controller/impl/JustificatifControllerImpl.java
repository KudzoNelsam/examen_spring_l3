package ism.absence.mobile.controller.impl;

import ism.absence.core.dto.response.RestResponse;
import ism.absence.data.models.Justificatif;
import ism.absence.mobile.controller.JustificatifController;
import ism.absence.mobile.dto.request.JustificatifRequestDTO;
import ism.absence.mobile.dto.response.JustificatifResponseDTO;

import ism.absence.services.JustificatifService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class JustificatifControllerImpl implements JustificatifController {

    private final JustificatifService justificatifService;

    @Override
    public ResponseEntity<?> submit(@Valid JustificatifRequestDTO dto) {
        try {
            log.info("🚀 Soumission d'un nouveau justificatif pour: {}", dto.getMatricule());

            // Validation supplémentaire
            if (dto.getMatricule() == null || dto.getMatricule().trim().isEmpty()) {
                log.warn("❌ Tentative de soumission sans matricule");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(RestResponse.response(
                                HttpStatus.BAD_REQUEST,
                                null,
                                "Le matricule est obligatoire"
                        ));
            }

            Justificatif justificatif = justificatifService.submit(dto);

            log.info("✅ Justificatif créé avec succès, ID: {}", justificatif.getId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(RestResponse.response(
                            HttpStatus.CREATED,
                            justificatif,
                            "Justificatif soumis avec succès"
                    ));

        } catch (IllegalArgumentException e) {
            log.warn("❌ Erreur de validation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(RestResponse.response(
                            HttpStatus.BAD_REQUEST,
                            null,
                            e.getMessage()
                    ));

        } catch (IllegalStateException e) {
            log.warn("❌ Erreur d'état: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(RestResponse.response(
                            HttpStatus.CONFLICT,
                            null,
                            e.getMessage()
                    ));

        } catch (Exception e) {
            log.error("❌ Erreur lors de la soumission du justificatif", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(RestResponse.response(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            null,
                            "Erreur interne du serveur"
                    ));
        }
    }

    @Override
    public ResponseEntity<?> getByEtudiant(@NotBlank String matricule) {
        try {
            log.info("📥 Récupération des justificatifs pour: {}", matricule);

            List<JustificatifResponseDTO> justificatifs = justificatifService.getByEtudiant(matricule);

            log.info("✅ {} justificatifs trouvés pour {}", justificatifs.size(), matricule);
            return ResponseEntity.ok(
                    RestResponse.response(
                            HttpStatus.OK,
                            justificatifs,
                            String.format("%d justificatif(s) trouvé(s)", justificatifs.size())
                    )
            );

        } catch (Exception e) {
            log.error("❌ Erreur lors de la récupération des justificatifs pour {}", matricule, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(RestResponse.response(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            null,
                            "Erreur lors de la récupération des justificatifs"
                    ));
        }
    }

    @Override
    public ResponseEntity<?> getById(@NotBlank String id) {
        try {
            log.info("📥 Récupération du justificatif: {}", id);

            Optional<JustificatifResponseDTO> justificatif = justificatifService.getById(id);

            if (justificatif.isPresent()) {
                log.info("✅ Justificatif {} trouvé", id);
                return ResponseEntity.ok(
                        RestResponse.response(
                                HttpStatus.OK,
                                justificatif.get(),
                                "Justificatif trouvé"
                        )
                );
            } else {
                log.warn("❌ Justificatif {} introuvable", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(RestResponse.response(
                                HttpStatus.NOT_FOUND,
                                null,
                                "Justificatif introuvable"
                        ));
            }

        } catch (Exception e) {
            log.error("❌ Erreur lors de la récupération du justificatif {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(RestResponse.response(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            null,
                            "Erreur lors de la récupération du justificatif"
                    ));
        }
    }

    @Override
    public ResponseEntity<?> getByEtudiantAndEtat(@NotBlank String matricule, @NotBlank String etat) {
        try {
            log.info("📥 Récupération des justificatifs pour {} avec état: {}", matricule, etat);

            List<JustificatifResponseDTO> justificatifs =
                    justificatifService.getByEtudiantAndEtat(matricule, etat);

            log.info("✅ {} justificatifs trouvés pour {} avec état {}",
                    justificatifs.size(), matricule, etat);
            return ResponseEntity.ok(
                    RestResponse.response(
                            HttpStatus.OK,
                            justificatifs,
                            String.format("%d justificatif(s) avec état %s trouvé(s)",
                                    justificatifs.size(), etat)
                    )
            );

        } catch (IllegalArgumentException e) {
            log.warn("❌ État invalide: {}", etat);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(RestResponse.response(
                            HttpStatus.BAD_REQUEST,
                            null,
                            e.getMessage()
                    ));

        } catch (Exception e) {
            log.error("❌ Erreur lors de la récupération des justificatifs par état pour {}", matricule, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(RestResponse.response(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            null,
                            "Erreur lors de la récupération des justificatifs"
                    ));
        }
    }

    @Override
    public ResponseEntity<?> getPendingByEtudiant(@NotBlank String matricule) {
        try {
            log.info("📥 Récupération des justificatifs en attente pour: {}", matricule);

            List<JustificatifResponseDTO> justificatifs =
                    justificatifService.getJustificatifsPendingForEtudiant(matricule);

            log.info("✅ {} justificatifs en attente trouvés pour {}", justificatifs.size(), matricule);
            return ResponseEntity.ok(
                    RestResponse.response(
                            HttpStatus.OK,
                            justificatifs,
                            String.format("%d justificatif(s) en attente trouvé(s)", justificatifs.size())
                    )
            );

        } catch (Exception e) {
            log.error("❌ Erreur lors de la récupération des justificatifs en attente pour {}", matricule, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(RestResponse.response(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            null,
                            "Erreur lors de la récupération des justificatifs en attente"
                    ));
        }
    }

    @Override
    public ResponseEntity<?> getStatsForEtudiant(@NotBlank String matricule) {
        try {
            log.info("📊 Récupération des statistiques pour: {}", matricule);

            long totalJustificatifs = justificatifService.countByEtudiant(matricule);

            // Récupérer les détails par état
            List<JustificatifResponseDTO> enCours =
                    justificatifService.getByEtudiantAndEtat(matricule, "EN_COURS");
            List<JustificatifResponseDTO> valides =
                    justificatifService.getByEtudiantAndEtat(matricule, "VALIDE");
            List<JustificatifResponseDTO> rejetes =
                    justificatifService.getByEtudiantAndEtat(matricule, "REJETE");

            Map<String, Object> stats = Map.of(
                    "totalJustificatifs", totalJustificatifs,
                    "enCours", enCours.size(),
                    "valides", valides.size(),
                    "rejetes", rejetes.size(),
                    "tauxValidation", totalJustificatifs > 0 ?
                            Math.round((valides.size() * 100.0) / totalJustificatifs) : 0
            );

            log.info("✅ Statistiques calculées pour {}: {}", matricule, stats);
            return ResponseEntity.ok(
                    RestResponse.response(
                            HttpStatus.OK,
                            stats,
                            "Statistiques récupérées avec succès"
                    )
            );

        } catch (Exception e) {
            log.error("❌ Erreur lors de la récupération des statistiques pour {}", matricule, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(RestResponse.response(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            null,
                            "Erreur lors de la récupération des statistiques"
                    ));
        }
    }

    @Override
    public ResponseEntity<?> updateEtat(@NotBlank String id, @NotBlank String etat) {
        try {
            log.info("🔄 Mise à jour de l'état du justificatif {} vers: {}", id, etat);

            boolean updated = justificatifService.updateEtat(id, etat);

            if (updated) {
                log.info("✅ État du justificatif {} mis à jour vers {}", id, etat);
                return ResponseEntity.ok(
                        RestResponse.response(
                                HttpStatus.OK,
                                Map.of("id", id, "nouvelEtat", etat),
                                "État mis à jour avec succès"
                        )
                );
            } else {
                log.warn("❌ Justificatif {} introuvable pour mise à jour", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(RestResponse.response(
                                HttpStatus.NOT_FOUND,
                                null,
                                "Justificatif introuvable"
                        ));
            }

        } catch (IllegalArgumentException e) {
            log.warn("❌ État invalide: {}", etat);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(RestResponse.response(
                            HttpStatus.BAD_REQUEST,
                            null,
                            e.getMessage()
                    ));

        } catch (Exception e) {
            log.error("❌ Erreur lors de la mise à jour de l'état du justificatif {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(RestResponse.response(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            null,
                            "Erreur lors de la mise à jour de l'état"
                    ));
        }
    }

    @Override
    public ResponseEntity<?> deleteById(@NotBlank String id) {
        try {
            log.info("🗑️ Suppression du justificatif: {}", id);

            // Vérifier d'abord que le justificatif existe
            Optional<JustificatifResponseDTO> existingJustificatif = justificatifService.getById(id);

            if (existingJustificatif.isEmpty()) {
                log.warn("❌ Justificatif {} introuvable pour suppression", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(RestResponse.response(
                                HttpStatus.NOT_FOUND,
                                null,
                                "Justificatif introuvable"
                        ));
            }

            // Vérifier si la suppression est autorisée (par ex: seulement si EN_COURS)
            JustificatifResponseDTO justificatif = existingJustificatif.get();
            if (!"EN_COURS".equals(justificatif.getEtat())) {
                log.warn("❌ Suppression non autorisée pour le justificatif {} (état: {})",
                        id, justificatif.getEtat());
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(RestResponse.response(
                                HttpStatus.FORBIDDEN,
                                null,
                                "Suppression non autorisée pour les justificatifs déjà traités"
                        ));
            }

            justificatifService.deleteById(id);

            log.info("✅ Justificatif {} supprimé avec succès", id);
            return ResponseEntity.ok(
                    RestResponse.response(
                            HttpStatus.OK,
                            Map.of("id", id),
                            "Justificatif supprimé avec succès"
                    )
            );

        } catch (IllegalArgumentException e) {
            log.warn("❌ {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(RestResponse.response(
                            HttpStatus.NOT_FOUND,
                            null,
                            e.getMessage()
                    ));

        } catch (Exception e) {
            log.error("❌ Erreur lors de la suppression du justificatif {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(RestResponse.response(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            null,
                            "Erreur lors de la suppression du justificatif"
                    ));
        }
    }
}