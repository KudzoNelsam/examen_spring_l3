package ism.absence.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Document(collection = "seance_cours")
public class SeanceCours {
    @Id
    private String id;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private LocalDate date;
//    Relation
    private String coursId;
    private String salleId;
}