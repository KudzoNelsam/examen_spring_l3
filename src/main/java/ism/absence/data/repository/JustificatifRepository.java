package ism.absence.data.repository;

import ism.absence.data.enums.EtatJustificatif;
import ism.absence.data.models.Justificatif;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JustificatifRepository extends MongoRepository<Justificatif, String> {
    List<Justificatif> findAllByMatricule(String matricule);

    List<Justificatif> findAllByEtat(EtatJustificatif etatJustificatif);
}