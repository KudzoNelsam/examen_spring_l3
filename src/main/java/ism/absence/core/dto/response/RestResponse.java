package ism.absence.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse <T>{
    private int status;
    private String type;
    private T data;
    private PaginationMeta pagination;
    private Map<String, String> errors;

    public static <T> RestResponse<T> of(HttpStatus status, T data, String type) {
        return RestResponse.<T>builder()
                .status(status.value())
                .data(data)
                .type(type)
                .build();
    }

    public static <T> RestResponse<T> paginate(Page<T> page, String type, HttpStatus status) {
        return RestResponse.<T>builder()
                .status(status.value())
                .data((T) page.getContent())
                .type(type)
                .pagination(PaginationMeta.of(page))
                .build();
    }

    public static RestResponse<Void> withErrors(BindingResult result, HttpStatus status) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }

        return RestResponse.<Void>builder()
                .status(status.value())
                .errors(errorMap)
                .type("error")
                .build();
    }

    @Data
    @Builder
    public static class PaginationMeta {
        private int currentPage;
        private int totalPages;
        private long totalElements;
        private boolean first;
        private boolean last;

        public static <T> PaginationMeta of(Page<T> page) {
            return PaginationMeta.builder()
                    .currentPage(page.getNumber())
                    .totalPages(page.getTotalPages())
                    .totalElements(page.getTotalElements())
                    .first(page.isFirst())
                    .last(page.isLast())
                    .build();
        }
    }
}