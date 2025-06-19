package ism.absence.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("clients")
public class Client {
    @Id
    private String id;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
}