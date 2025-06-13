package ism.absence.data.repository;

import ism.absence.data.models.Ecole;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EcoleRepository extends MongoRepository<Ecole,String> {
}