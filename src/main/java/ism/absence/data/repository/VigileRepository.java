package ism.absence.data.repository;

import ism.absence.data.models.Vigile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VigileRepository extends MongoRepository<Vigile,String> {
}