package ism.absence.core.exceptions;

import ism.absence.core.dto.response.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<RestResponse<Void>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                RestResponse.<Void>builder()
                        .status(HttpStatus.CONFLICT.value())
                        .type("user_already_exists")
                        .errors(Map.of("username", ex.getMessage()))
                        .build()
        );
    }

    // Ajout d’un gestionnaire générique (optionnel, mais recommandé)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<Void>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                RestResponse.<Void>builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .type("internal_server_error")
                        .errors(Map.of("error", ex.getMessage()))
                        .build()
        );
    }
}