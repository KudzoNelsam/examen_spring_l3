package ism.absence.data.models;

import ism.absence.data.enums.Grade;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "classes")
public class Classe {
    @Id
    private String id;
    private String libelle;
    private Grade grade;
    //    Relation
    private String filiereId;
    private String coursId;
}