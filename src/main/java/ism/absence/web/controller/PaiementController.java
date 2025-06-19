package ism.absence.web.controller;

import ism.absence.web.dto.request.PaiementRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("api/paiements")
public interface PaiementController {

   @PostMapping("")
    ResponseEntity<?>  createPaiement(@RequestBody PaiementRequest paiementRequest);

   @GetMapping("/flitrer")
    ResponseEntity<?>  getPaiementsByFiltrer(@RequestParam(defaultValue = "") String telephone,
                                       @RequestParam(defaultValue = "") String numeroDette,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int size);

}