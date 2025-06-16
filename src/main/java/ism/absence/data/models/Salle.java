package ism.absence.data.models;

import ism.absence.data.records.Localisation;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "salles")
@Data
public class Salle {
    @Id
    private String id;
    private String nom;
    private String adresse;
    private Localisation localisation;
    private int capacite;
}