package ism.absence.web.controller.impl;

import ism.absence.core.dto.response.RestResponse;
import ism.absence.data.models.Client;
import ism.absence.services.ClientService;
import ism.absence.web.controller.ClientController;
import ism.absence.web.dto.request.ClientRequest;
import ism.absence.web.dto.response.ClientResponseDTO;
import ism.absence.web.utils.mappers.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientControllerImpl implements ClientController {
    private final ClientService clientService;


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
    public ResponseEntity<?> save(ClientRequest client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(RestResponse.responseError(bindingResult), HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<?> findById(String id) {
        return new ResponseEntity<>(RestResponse.response(
                HttpStatus.OK,
                ClientMapper.INSTANCE.toClientResponseDTO(clientService.findById(id)),
                ClientResponseDTO.class.getTypeName()
        ), HttpStatus.OK);
    }
}