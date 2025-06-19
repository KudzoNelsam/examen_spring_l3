package ism.absence.data.repository;

import ism.absence.data.models.Dette;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DetteRepository extends MongoRepository<Dette, String> {
}