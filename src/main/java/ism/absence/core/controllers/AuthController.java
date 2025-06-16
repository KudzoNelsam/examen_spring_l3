package ism.absence.core.controllers;

import ism.absence.core.dto.request.UserAuthRequestDTO;
import ism.absence.core.dto.request.UserRequestDTO;
import ism.absence.core.dto.response.RestResponse;
import ism.absence.core.dto.response.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/api/auth")
public interface AuthController {
    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody UserRequestDTO userRequestDto);

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody UserAuthRequestDTO user);

    @GetMapping("/me")
    ResponseEntity<?> getInformation();
}