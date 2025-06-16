package ism.absence.data.repository;

import ism.absence.data.models.Classe;
import ism.absence.data.models.Cours;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClasseRepository extends MongoRepository<Classe, String> {
    Classe findByCoursId(String coursId);
}