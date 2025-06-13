package ism.absence.data.repository;

import ism.absence.data.models.Etudiant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EtudiantRepository extends MongoRepository<Etudiant, String> {
}