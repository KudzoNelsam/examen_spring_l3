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
    private String anneeScolaireId;
    private String etudiantId;
    private String classeId;
    private boolean active = true;
}