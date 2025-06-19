package ism.absence.data.repository;

import ism.absence.data.models.Ligne;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LigneRepository extends MongoRepository<Ligne, String> {
}