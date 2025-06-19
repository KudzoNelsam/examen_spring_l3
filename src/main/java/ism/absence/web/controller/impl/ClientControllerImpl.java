package ism.absence.web.controller.impl;

import ism.absence.core.dto.response.RestResponse;
import ism.absence.data.models.Article;
import ism.absence.data.models.Client;
import ism.absence.data.models.Dette;
import ism.absence.data.models.Ligne;
import ism.absence.data.repository.ArticleRepository;
import ism.absence.data.repository.DetteRepository;
import ism.absence.data.repository.LigneRepository;
import ism.absence.services.ClientService;
import ism.absence.web.controller.ClientController;
import ism.absence.web.dto.request.ClientRequest;
import ism.absence.web.dto.request.ClientWithDetteRequest;
import ism.absence.web.dto.request.DetteRequest;
import ism.absence.web.dto.request.LigneRequest;
import ism.absence.web.dto.response.ClientResponseDTO;
import ism.absence.web.dto.response.ClientWithDetteResponse;
import ism.absence.web.dto.response.DetteResponseDTO;
import ism.absence.web.utils.mappers.ClientMapper;
import ism.absence.web.utils.mappers.DetteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ClientControllerImpl implements ClientController {
    private final ClientService clientService;
    private final DetteRepository detteRepository;
    private final LigneRepository ligneRepository;
    private final ArticleRepository articleRepository;

    @Override
    @Transactional
    public ResponseEntity<?> saveWithDettes(ClientWithDetteRequest clientWithDetteRequest, BindingResult bindingResult) {
        // Validation
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(RestResponse.responseError(bindingResult), HttpStatus.BAD_REQUEST);
        }

        // Vérifier le client
        Client client = clientService.findById(clientWithDetteRequest.getClientId());
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(RestResponse.responseError2("Client non trouvé"));
        }

        List<Dette> dettesEntity = new ArrayList<>();

        try {
            // Traiter chaque dette
            for (DetteRequest detteRequest : clientWithDetteRequest.getDettes()) {
                Dette dette = DetteMapper.INSTANCE.toDette(detteRequest);
                dette.setClientId(client.getId());

                // Initialiser les montants
                double montantTotal = 0.0;
                List<Ligne> lignes = new ArrayList<>();

                // Traiter toutes les lignes de cette dette
                for (LigneRequest ligneRequest : detteRequest.getLigneRequests()) {
                    // Récupérer l'article
                    Article article = articleRepository.findById(ligneRequest.getArticleId())
                            .orElseThrow(() -> new RuntimeException("Article non trouvé: " + ligneRequest.getArticleId()));

                    // Vérifier le stock
                    if (article.getQteStock() < ligneRequest.getQteCom()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(RestResponse.responseError2(
                                        String.format("Stock insuffisant pour l'article %s. Disponible: %d, Demandé: %d",
                                                article.getLibelle(), article.getQteStock(), ligneRequest.getQteCom())
                                ));
                    }

                    // Créer la ligne
                    Ligne ligne = new Ligne();
                    ligne.setQteCom(ligneRequest.getQteCom());
                    ligne.setArticleId(article.getId());

                    // Calculer le montant de cette ligne pour le total
                    montantTotal += article.getPrixVente() * ligneRequest.getQteCom();

                    // Mettre à jour le stock
                    article.setQteStock(article.getQteStock() - ligneRequest.getQteCom());
                    articleRepository.save(article);

                    lignes.add(ligne);
                }

                // Définir les montants de la dette
                dette.setMontantDette(montantTotal);
                dette.setMontantRestant(montantTotal);
                dette.setMontantPaye(0.0);
                dette.setNumero(client.getNom() + LocalDate.now().toString());
                dette.setDate(LocalDate.now());
                // Sauvegarder la dette
                dette = detteRepository.save(dette);

                dettesEntity.add(dette);

                // Sauvegarder toutes les lignes avec l'ID de la dette
                for (Ligne ligne : lignes) {
                    ligne.setDetteId(dette.getId());
                    ligneRepository.save(ligne);
                }
            }

            // Construire la réponse
            ClientWithDetteResponse clientWithDetteResponse = new ClientWithDetteResponse();
            clientWithDetteResponse.setClient(ClientMapper.INSTANCE.toClientResponseDTO(client));

            List<DetteResponseDTO> detteResponseDTOS = new ArrayList<>();

            for (Dette dette : dettesEntity){
                DetteResponseDTO detteResponseDTO = new DetteResponseDTO();
                detteResponseDTO.setId(dette.getId());
                detteResponseDTO.setDate(dette.getDate());
                detteResponseDTO.setMontantDette(dette.getMontantDette());
                detteResponseDTO.setMontantRestant(dette.getMontantRestant());
                detteResponseDTO.setMontantPaye(dette.getMontantPaye());
                detteResponseDTO.setNumero(dette.getNumero());
                detteResponseDTOS.add(detteResponseDTO);
            }

            clientWithDetteResponse.setDettes(detteResponseDTOS);

            return new ResponseEntity<>(
                    RestResponse.response(
                            HttpStatus.CREATED,
                            clientWithDetteResponse,
                            "Dettes créées avec succès"
                    ),
                    HttpStatus.CREATED
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(RestResponse.responseError2("Erreur lors de la création des dettes: " + e.getMessage()));
        }
    }

    private double getTotalFromLignes(List<Ligne> lignes) {
        double total = 0;
        for (Ligne ligne : lignes) {
            double s = ligne.getQteCom() * articleRepository.findById(ligne.getArticleId()).get().getPrixVente();
            total += s;
        }
        return total;
    }

    @Override
    public ResponseEntity<?> findWithDettes(String id, int page, int size) {
        Client client = clientService.findById(id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Dette> pageDette = detteRepository.findDettesByClientId(id, pageable);

        List<Dette> dettes = pageDette.getContent();
        List<DetteResponseDTO> dettesDto = new ArrayList<>();

        for (Dette dette : dettes) {
            DetteResponseDTO detteResponseDTO = new DetteResponseDTO();
            detteResponseDTO.setId(dette.getId());
            detteResponseDTO.setDate(dette.getDate());
            detteResponseDTO.setNumero(dette.getNumero());
            detteResponseDTO.setMontantDette(dette.getMontantDette());
            detteResponseDTO.setMontantRestant(dette.getMontantRestant());
            detteResponseDTO.setMontantPaye(dette.getMontantPaye());
            dettesDto.add(detteResponseDTO);
        }

        System.out.println(dettesDto);
        ClientWithDetteResponse result = new ClientWithDetteResponse();
        result.setClient(ClientMapper.INSTANCE.toClientResponseDTO(client));
        result.setDettes(dettesDto);

        return new ResponseEntity<>(RestResponse.responsePaginate(
                HttpStatus.OK,
                result,
                pageDette.getNumber(),
                pageDette.getTotalPages(),
                pageDette.getTotalElements(),
                pageDette.hasPrevious(),
                pageDette.hasNext(),
                ClientWithDetteResponse.class.getTypeName()
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        return new ResponseEntity<>(RestResponse.response(
                HttpStatus.CREATED,
                clientService.deleteById(id),
                ClientResponseDTO.class.getTypeName()
        ), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> update(ClientRequest client, String id) {
        return new ResponseEntity<>(RestResponse.response(
                HttpStatus.CREATED,
                ClientMapper.INSTANCE
                        .toClientResponseDTO(clientService
                                .save(ClientMapper.INSTANCE
                                        .toClient2(client))),
                ClientResponseDTO.class.getTypeName()
        ), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Client> pageClient = clientService.findAll(pageable);
        List<ClientResponseDTO> data = pageClient.getContent().stream().map(ClientMapper.INSTANCE::toClientResponseDTO).toList();

        return new ResponseEntity<>(RestResponse.responsePaginate(
                HttpStatus.OK,
                data,
                pageClient.getNumber(),
                pageClient.getTotalPages(),
                pageClient.getTotalElements(),
                pageClient.hasPrevious(),
                pageClient.hasNext(),
                ClientResponseDTO.class.getTypeName()
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> save(ClientRequest clientRequest, BindingResult bindingResult) {
        System.out.println(bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(RestResponse.responseError(bindingResult), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    ClientMapper.INSTANCE
                            .toClientResponseDTO(clientService
                                    .save(ClientMapper.INSTANCE
                                            .toClient2(clientRequest))),
                    ClientResponseDTO.class.getTypeName()
            ), HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<?> findById(String id) {
        return new ResponseEntity<>(RestResponse.response(
                HttpStatus.OK,
                ClientMapper.INSTANCE.toClientResponseDTO(clientService.findById(id)),
                ClientResponseDTO.class.getTypeName()
        ), HttpStatus.OK);
    }
}