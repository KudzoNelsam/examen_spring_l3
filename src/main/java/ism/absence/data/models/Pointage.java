package ism.absence.data.models;

import ism.absence.data.enums.StatutPointage;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "pointages")
public class Pointage {
    @Id
    private String id;
    private LocalDateTime date;
    private StatutPointage statut;
//    Relation
    private String vigileId;
    private String seanceCoursId;
    private String matricule;
    private boolean estJustifie = false;
}