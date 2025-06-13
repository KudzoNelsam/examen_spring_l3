package ism.absence.data.models;

import ism.absence.data.enums.StatutAnnee;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "annee_scolaires")
@Data
public class AnneeScolaire {
    @Id
    private String id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutAnnee statut;
//
}