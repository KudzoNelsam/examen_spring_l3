package ism.absence.services.impl;

import ism.absence.data.models.Client;
import ism.absence.data.repository.ClientRepository;
import ism.absence.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Override
    public Client findById(String id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public Client findByPrenom(String prenom) {
        return clientRepository.findByPrenom(prenom);
    }

    @Override
    public Client findByTelephone(String telephone) {
        return clientRepository.findByTelephone(telephone);
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Page<Client> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public void deleteAll() {
        clientRepository.deleteAll();
    }

    @Override
    public boolean deleteById(String id) {
        clientRepository.deleteById(id);
        return true;
    }

    @Override
    public void saveAll(List<Client> clients) {
        clientRepository.saveAll(clients);
    }
}