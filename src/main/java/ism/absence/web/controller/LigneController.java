package ism.absence.web.controller;

import ism.absence.web.dto.request.ClientRequest;
import ism.absence.web.dto.request.LigneRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("api/lignes")
public interface LigneController {

    @GetMapping("")
    ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    );

    @PostMapping("")
    ResponseEntity<?> save(@RequestBody LigneRequest ligneRequest);

    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable String id);

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable String id);

    @PutMapping("/{id}")
    ResponseEntity<?> update(@RequestBody LigneRequest ligneRequest,  @PathVariable String id);


}