package ism.absence.web.controller;

import ism.absence.web.dto.request.ClientRequest;
import ism.absence.web.dto.request.DetteRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RequestMapping("api/dettes")
public interface DetteController {

    @GetMapping("")
    ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    );

    @PostMapping("")
    ResponseEntity<?> save(@RequestBody DetteRequest detteRequest);

    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable String id);

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable String id);

    @PutMapping("/{id}")
    ResponseEntity<?> update(@RequestBody ClientRequest client, @PathVariable String id);

    @GetMapping("/{id}/paiements")
    ResponseEntity<?> findWithPaiements(@PathVariable String id,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size);


}