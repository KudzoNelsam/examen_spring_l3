package ism.absence.data.repository;

import ism.absence.data.models.Paiement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaiementRepository extends MongoRepository<Paiement, String> {
    Page<Paiement> findByDetteId(String detteId, Pageable pageable);
}