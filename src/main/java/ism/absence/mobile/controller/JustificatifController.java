package ism.absence.mobile.controller;

import ism.absence.mobile.dto.request.JustificatifRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/justificatifs")
@CrossOrigin(origins = "*") // À configurer selon vos besoins
public interface JustificatifController {

    // Soumettre un justificatif
    @PostMapping("")
    ResponseEntity<?> submit(@Valid @RequestBody JustificatifRequestDTO dto);

    // Récupérer tous les justificatifs d'un étudiant
    @GetMapping("/etudiant/{matricule}")
    ResponseEntity<?> getByEtudiant(@PathVariable @NotBlank String matricule);

    // Récupérer un justificatif par son id
    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable @NotBlank String id);

    // Filtrer par état
    @GetMapping("/etudiant/{matricule}/etat/{etat}")
    ResponseEntity<?> getByEtudiantAndEtat(
            @PathVariable @NotBlank String matricule,
            @PathVariable @NotBlank String etat
    );

    // Justificatifs en attente
    @GetMapping("/etudiant/{matricule}/pending")
    ResponseEntity<?> getPendingByEtudiant(@PathVariable @NotBlank String matricule);

    // Statistiques
    @GetMapping("/etudiant/{matricule}/stats")
    ResponseEntity<?> getStatsForEtudiant(@PathVariable @NotBlank String matricule);

    // Mise à jour d'état (pour admin)
    @PutMapping("/{id}/etat")
    ResponseEntity<?> updateEtat(
            @PathVariable @NotBlank String id,
            @RequestParam @NotBlank String etat
    );

    // Suppression (si autorisée)
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@PathVariable @NotBlank String id);
}