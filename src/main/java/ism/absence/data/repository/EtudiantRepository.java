package ism.absence.data.repository;

import ism.absence.data.models.Etudiant;
import ism.absence.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EtudiantRepository extends MongoRepository<Etudiant, String> {

    Optional<Etudiant> findByMatricule(String matricule);
    Optional<Etudiant> findByUserId(String userId);
}