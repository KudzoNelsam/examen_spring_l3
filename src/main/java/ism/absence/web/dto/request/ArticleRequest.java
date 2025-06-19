package ism.absence.web.dto.request;

import lombok.Data;

@Data
public class ArticleRequest {
    private String libelle;
    private double qteStock;
    private double prixVente;
}