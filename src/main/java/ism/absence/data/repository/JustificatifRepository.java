package ism.absence.data.repository;

import ism.absence.data.models.Justificatif;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JustificatifRepository extends MongoRepository<Justificatif, String> {
}