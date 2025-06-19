package ism.absence.data.repository;

import ism.absence.data.models.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<Client, String> {
    Client findByNom(String nom);

    Client findByPrenom(String prenom);

    Client findByTelephone(String telephone);
}