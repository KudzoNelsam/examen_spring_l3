package ism.absence.core.controllers.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import ism.absence.config.JwtUtil;
import ism.absence.core.controllers.AuthController;
import ism.absence.core.dto.request.UserAuthRequestDTO;
import ism.absence.core.dto.request.UserRequestDTO;
import ism.absence.core.dto.response.RestResponse;
import ism.absence.core.dto.response.UserResponseDTO;
import ism.absence.data.enums.UserRole;
import ism.absence.data.models.*;
import ism.absence.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "User Management", description = "APIs for managing users")

@RequiredArgsConstructor
@RestController
public class AuthControllerImpl implements AuthController {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    public ResponseEntity<?> getInformation() {
        return null;
    }

    @Override
    public ResponseEntity<?> register(@Validated @RequestBody UserRequestDTO userRequestDto) {
        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(userRequestDto.getPassword());
        user.setFullName(userRequestDto.getFullName());
        user.setAdresse(userRequestDto.getAdresse());
        user.setRole(UserRole.valueOf(userRequestDto.getRole()));
        user = userService.save(user);
        String token = jwtUtil.generateToken(user.getUsername());
        UserResponseDTO authResponse = UserResponseDTO.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestResponse.response(HttpStatus.CREATED, authResponse, "user_registered")
        );
    }


    @Override
    public ResponseEntity<?> login(@Validated @RequestBody UserAuthRequestDTO request) {
        Optional<User> userOpt = userService.login(request.getUsername(), request.getPassword());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    RestResponse.response(HttpStatus.UNAUTHORIZED, null, "invalid_credentials")
            );
        }
        User user = userOpt.get();
        String token = jwtUtil.generateToken(user.getUsername());
        UserResponseDTO authResponse = UserResponseDTO.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
        return ResponseEntity.ok(
                RestResponse.response(HttpStatus.OK, authResponse, "login_success")
        );
    }
}