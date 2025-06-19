package ism.absence.data.repository;

import ism.absence.data.models.Dette;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DetteRepository extends MongoRepository<Dette, String> {
    Page<Dette> findDettesByClientId(String clientId, Pageable pageable);

    Dette findByNumero(String numero);
}