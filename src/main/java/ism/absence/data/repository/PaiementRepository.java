package ism.absence.data.repository;

import ism.absence.data.models.Paiement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaiementRepository extends MongoRepository<Paiement, String> {
}