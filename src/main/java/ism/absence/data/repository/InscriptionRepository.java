package ism.absence.data.repository;

import ism.absence.data.models.Inscription;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InscriptionRepository extends MongoRepository<Inscription, String> {
}