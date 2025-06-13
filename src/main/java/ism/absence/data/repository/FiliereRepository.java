package ism.absence.data.repository;

import ism.absence.data.models.Filiere;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FiliereRepository extends MongoRepository<Filiere,String> {
}