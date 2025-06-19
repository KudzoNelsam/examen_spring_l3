package ism.absence.web.controller.impl;

import ism.absence.web.controller.PaiementController;
import ism.absence.web.dto.request.PaiementRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaiementControllerImpl implements PaiementController {
    @Override
    public ResponseEntity<?> findAll(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<?> save(PaiementRequest paiementRequest) {
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
    public ResponseEntity<?> update(PaiementRequest paiementRequest, String id) {
        return null;
    }
}