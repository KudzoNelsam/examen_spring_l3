package ism.absence.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LigneRequest {
    @NotBlank(message = "La quantité commandée est obligatoire")
    private int qteCom;
    @NotBlank(message = "L'article est obligatoire")
    private String articleId;
    private String detteId;
}