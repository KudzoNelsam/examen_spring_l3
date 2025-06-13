package ism.absence.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document(collection = "ecoles")
public class Ecole {
    @Id
    private String id;
    private String nomEcole;
    private Map<String, Double> localisation;
}