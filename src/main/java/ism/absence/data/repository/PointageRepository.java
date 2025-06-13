package ism.absence.data.repository;

import ism.absence.data.models.Pointage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PointageRepository extends MongoRepository<Pointage, String> {
}