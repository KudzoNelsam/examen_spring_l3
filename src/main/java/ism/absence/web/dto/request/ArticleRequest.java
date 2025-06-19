package ism.absence.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArticleRequest {
    @NotBlank(message = "Le libelle est obligatoire")
    private String libelle;
    @NotBlank( message = "La quantité est obligatoire")
    private double qteStock;
    @NotBlank( message = "Le prix de vente est obligatoire")
    private double prixVente;
}