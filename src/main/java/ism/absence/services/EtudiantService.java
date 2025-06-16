package ism.absence.services;

import ism.absence.data.models.Etudiant;
import ism.absence.data.models.User;

import java.util.List;

public interface EtudiantService {
    Etudiant save(Etudiant etudiant);
    Etudiant findByMatricule(String matricule);
//    Etudiant findByNom(String nom);
    List<Etudiant> findAll();
    Etudiant findByUserId(String userId);
}