package ism.absence.web.controller.impl;

import ism.absence.web.controller.LigneController;
import ism.absence.web.dto.request.LigneRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LigneControllerImpl implements LigneController {
    @Override
    public ResponseEntity<?> findAll(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<?> save(LigneRequest ligneRequest) {
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
    public ResponseEntity<?> update(LigneRequest ligneRequest, String id) {
        return null;
    }
}