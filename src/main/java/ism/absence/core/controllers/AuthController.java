package ism.absence.core.controllers;

import ism.absence.core.dto.request.UserAuthRequestDTO;
import ism.absence.core.dto.request.UserRequestDTO;
import ism.absence.core.dto.response.UserResponseDTO;
import ism.absence.core.dto.response.RestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/api/auth")
public interface AuthController {
    @PostMapping("/register")
    ResponseEntity<RestResponse<UserResponseDTO>> register(@RequestBody UserRequestDTO userRequestDto);
    @PostMapping("/login")
    public ResponseEntity<RestResponse<UserResponseDTO>> login(@RequestBody UserAuthRequestDTO user);
}