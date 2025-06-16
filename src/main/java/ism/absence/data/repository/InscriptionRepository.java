package ism.absence.data.repository;

import ism.absence.data.models.Inscription;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface InscriptionRepository extends MongoRepository<Inscription, String> {
    Inscription findByEtudiantId(String etudiantId);

    Optional<Inscription> findByEtudiantIdAndAnneeScolaireId(String id, String id1);
}