package ism.absence.data.mock;

import com.github.javafaker.Faker;
import ism.absence.data.models.Client;
import ism.absence.services.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final ClientService clientService;
    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        clientService.deleteAll();
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i <10; i++) {
            Client client = new Client();
            client.setNom(faker.name().lastName());
            client.setPrenom(faker.name().firstName());
            client.setAdresse(faker.address().cityName());
            client.setTelephone(faker.phoneNumber().cellPhone());
            clients.add(client);
        }
        clientService.saveAll(clients);
    }
}