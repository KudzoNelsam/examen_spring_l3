package ism.absence.data.repository;

import ism.absence.data.models.AnneeScolaire;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnneeScolaireRepository extends MongoRepository<AnneeScolaire, String> {
}