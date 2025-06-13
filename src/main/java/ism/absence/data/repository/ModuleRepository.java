package ism.absence.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ism.absence.data.models.Module;

public interface ModuleRepository extends MongoRepository<Module, String> {
}