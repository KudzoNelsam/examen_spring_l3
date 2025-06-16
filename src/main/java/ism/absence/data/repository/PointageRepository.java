package ism.absence.data.repository;

import ism.absence.data.enums.StatutPointage;
import ism.absence.data.models.Pointage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PointageRepository extends MongoRepository<Pointage, String> {
    List<Pointage> findByMatricule(String matricule);

    boolean existsByMatriculeAndSeanceCoursId(String matricule, String seanceCoursId);

    Pointage findByMatriculeAndSeanceCoursIdAndStatut(String matricule, String seanceCoursId, StatutPointage statutPointage);
}