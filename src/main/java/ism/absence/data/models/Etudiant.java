package ism.absence.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "etudiants")
public class Etudiant {
    @Id
    private String id;
    private String matricule;
    private String userId;
}