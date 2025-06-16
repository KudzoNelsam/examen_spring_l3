package ism.absence.mobile.controller;

import ism.absence.mobile.dto.request.JustificatifRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/justificatifs")
public interface JustificatifController {
    // Soumettre un justificatif (plusieurs pièces jointes possibles)
    @PostMapping("")
    ResponseEntity<?> submit(@RequestBody JustificatifRequestDTO dto);

    // Récupérer tous les justificatifs d'un étudiant
    @GetMapping("/etudiant/{matricule}")
    ResponseEntity<?> getByEtudiant(@PathVariable String matricule);

    // (Optionnel) : Récupérer un justificatif par son id
    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable String id);
}