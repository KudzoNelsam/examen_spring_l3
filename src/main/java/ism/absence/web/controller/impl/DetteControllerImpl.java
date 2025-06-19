package ism.absence.web.controller.impl;

import ism.absence.web.controller.DetteController;
import ism.absence.web.dto.request.ClientRequest;
import ism.absence.web.dto.request.DetteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DetteControllerImpl implements DetteController {
    @Override
    public ResponseEntity<?> findAll(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<?> save(DetteRequest detteRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> findById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> update(ClientRequest client, String id) {
        return null;
    }
}