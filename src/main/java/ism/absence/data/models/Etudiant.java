package ism.absence.data.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "etudiants")
public class Etudiant extends User {
//    @Id
//    private String etudiantId;
    private String matricule;
}