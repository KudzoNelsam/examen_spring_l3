package ism.absence.web.dto.response;

import lombok.Data;

@Data
public class ArticleResponseDTO {
    private String libelle;
    private double qteStock;
    private double prixVente;
}