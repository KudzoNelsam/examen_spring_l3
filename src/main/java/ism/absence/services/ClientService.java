package ism.absence.services;

import ism.absence.data.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {
    Client findById(String id);

    Client findByPrenom(String prenom);

    Client findByTelephone(String telephone);

    Client save(Client client);

    List<Client> findAll();

    Page<Client> findAll(Pageable pageable);

    void deleteAll();

    boolean deleteById(String id);

    void saveAll(List<Client> clients);

}