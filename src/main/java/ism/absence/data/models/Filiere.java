package ism.absence.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "filieres")
public class Filiere {
    @Id
    private String id;
    private String nomFiliere;
    private String codeFiliere;
}