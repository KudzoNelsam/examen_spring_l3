package ism.absence.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "inscriptions")
public class Inscription {
    @Id
    private String id;
    private LocalDate dateInscription;
    private AnneeScolaire anneeScolaire;
    private Etudiant etudiant;
    private Classe classe;
}