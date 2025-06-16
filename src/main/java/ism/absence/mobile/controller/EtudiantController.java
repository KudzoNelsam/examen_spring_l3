package ism.absence.mobile.controller;

import ism.absence.mobile.dto.request.EtudiantRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/etudiants")
public interface EtudiantController {
    @PostMapping("")
    ResponseEntity<?> save(@RequestBody EtudiantRequestDTO etudiant);

    @GetMapping("/{matricule}")
    ResponseEntity<?> getByMatricule(@PathVariable String matricule);

    @GetMapping("/{matricule}/justificatifs")
    ResponseEntity<?> getJustificatifs(@PathVariable String matricule);

    @GetMapping("/etudiants/{matricule}/pointages")
    ResponseEntity<?> getPointages(@PathVariable String matricule);

}