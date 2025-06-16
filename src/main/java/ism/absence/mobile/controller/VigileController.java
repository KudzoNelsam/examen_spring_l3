package ism.absence.mobile.controller;

import ism.absence.mobile.dto.request.PointageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/vigile")
public interface VigileController {
    @PutMapping("/check")
    ResponseEntity<?> pointer(@RequestBody PointageRequest pointageRequest);
}