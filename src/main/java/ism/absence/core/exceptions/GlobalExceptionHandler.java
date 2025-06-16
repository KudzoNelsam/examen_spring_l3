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
    public ResponseEntity<?> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                RestResponse.response(
                        HttpStatus.CONFLICT,
                        Map.of("username", ex.getMessage()),
                        "user_already_exists"
                )
        );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                RestResponse.response(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        Map.of("error", ex.getMessage()),
                        "internal_server_error"
                )
        );
    }
}