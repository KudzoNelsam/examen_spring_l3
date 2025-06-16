package ism.absence.mobile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/admin")
public interface AdminController {

    // Valider un justificatif (absence ou retard)
    @PutMapping("/justificatifs/{id}/valider")
    ResponseEntity<?> validerJustificatif(@PathVariable String id);

    // Refuser un justificatif (absence ou retard)
    @PutMapping("/justificatifs/{id}/refuser")
    ResponseEntity<?> refuserJustificatif(@PathVariable String id);

    // (Optionnel) Liste des justificatifs en attente de validation
    @GetMapping("/justificatifs/en-attente")
    ResponseEntity<?> getJustificatifsEnAttente();


}