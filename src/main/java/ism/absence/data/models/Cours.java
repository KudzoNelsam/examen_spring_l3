package ism.absence.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cours")
@Data
public class Cours {
    @Id
    private String id;
    private String nom;
    //    Relation
    private Module module;
}