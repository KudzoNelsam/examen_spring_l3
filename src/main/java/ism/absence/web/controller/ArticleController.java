package ism.absence.web.controller;

import ism.absence.web.dto.request.ArticleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("api/articles")
public interface ArticleController {

    @GetMapping("")
    ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    );

    @PostMapping("")
    ResponseEntity<?> save(@RequestBody ArticleRequest articleRequest);

    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable String id);

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable String id);

    @PutMapping("/{id}")
    ResponseEntity<?> update(@RequestBody ArticleRequest articleRequest, @PathVariable String id);


}