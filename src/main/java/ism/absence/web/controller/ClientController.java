package ism.absence.web.controller;

import ism.absence.data.models.Client;
import ism.absence.web.dto.request.ClientRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RequestMapping("api/clients")
public interface ClientController {

    @GetMapping("")
    ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    );

    @PostMapping("")
    ResponseEntity<?> save(@Valid @RequestBody ClientRequest client, BindingResult bindingResult);

    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable String id);

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable String id);

    @PutMapping("/{id}")
    ResponseEntity<?> update(@RequestBody ClientRequest client,  @PathVariable String id);


}