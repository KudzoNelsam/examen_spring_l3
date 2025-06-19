package ism.absence.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Article {
    @Id
    private String id;
    private String libelle;
    private int qteStock;
    private double prixVente;
}