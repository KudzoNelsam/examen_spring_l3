package ism.absence.web.controller.impl;

import ism.absence.web.controller.ArticleController;
import ism.absence.web.dto.request.ArticleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ArticleControllerImpl implements ArticleController {
    @Override
    public ResponseEntity<?> findAll(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<?> save(ArticleRequest articleRequest) {
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
    public ResponseEntity<?> update(ArticleRequest articleRequest, String id) {
        return null;
    }
}