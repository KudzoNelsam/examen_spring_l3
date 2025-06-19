package ism.absence.data.mock;

import com.github.javafaker.Faker;
import ism.absence.data.models.Article;
import ism.absence.data.models.Client;
import ism.absence.data.models.Dette;
import ism.absence.data.models.Ligne;
import ism.absence.data.repository.ArticleRepository;
import ism.absence.data.repository.DetteRepository;
import ism.absence.data.repository.LigneRepository;
import ism.absence.services.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final ClientService clientService;
    private final ArticleRepository articleRepository;
    private final DetteRepository detteRepository;
    private final LigneRepository ligneRepository;
    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        clientService.deleteAll();
        detteRepository.deleteAll();
        ligneRepository.deleteAll();
        articleRepository.deleteAll();
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i <2; i++) {
            Client client = new Client();
            client.setNom(faker.name().lastName());
            client.setPrenom(faker.name().firstName());
            client.setAdresse(faker.address().cityName());
            client.setTelephone(faker.phoneNumber().cellPhone());
            clients.add(client);
        }
        clientService.saveAll(clients);
        List<Article> articles = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Article article = new Article();
            article.setLibelle("Article" + i);
            article.setQteStock(100*i);
            article.setPrixVente(300.0 * i);
            articles.add(article);
        }
        articleRepository.saveAll(articles);


        for (Client client : clients) {
            List<Dette> dettes = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                Dette dette = new Dette();
                List<Ligne> lignes = new ArrayList<>();
                for (Article article : articles) {
                    Ligne ligne = new Ligne();
                    ligne.setArticleId(article.getId());
                    ligne.setQteCom(5);
                    article.setQteStock(article.getQteStock() - ligne.getQteCom());
                    lignes.add(ligne);
                }
                dette.setMontantDette(getTotalFromLignes(lignes));
                dette.setMontantRestant(dette.getMontantDette());
                dette.setMontantPaye(0);
                dette.setClientId(client.getId());
                dette.setNumero("Dette"+i);
                dette.setDate(LocalDate.now());
                dette =  detteRepository.save(dette);
                for (Ligne ligne : lignes) {
                    ligne.setDetteId(dette.getId());
                }
                ligneRepository.saveAll(lignes);
            }
        }

    }

    private double getTotalFromLignes(List<Ligne> lignes){
        double total = 0;
        for(Ligne ligne : lignes){
            double s = ligne.getQteCom() * articleRepository.findById(ligne.getArticleId()).get().getPrixVente();
            total += s;
        }
        return total;
    }
}