package ism.absence.core.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class RestResponse {

    public static Map<String, Object> response(HttpStatus status, Object data, String type) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("result", data);
        response.put("type", type);
        return response;
    }

    public static Map<String, Object> responseError(BindingResult bindingResult) {
        Map<String, Object> errors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("type", "error");
        response.put("errors", errors);
        return response;
    }

    public static Map<String, Object> responsePaginate(HttpStatus status,
                                                       Object results,
                                                       int currentPage,
                                                       int totalPages,
                                                       long totalElements,
                                                       boolean first,
                                                       boolean last,
                                                       String type) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("results", results);
        response.put("currentPage", currentPage);
        response.put("totalPages", totalPages);
        response.put("totalElements", totalElements);
        response.put("first", first);
        response.put("last", last);
        response.put("type", type);
        return response;
    }
}