package ism.absence.data.repository;

import ism.absence.data.models.SeanceCours;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SeanceCoursRepository extends MongoRepository<SeanceCours, String> {
}