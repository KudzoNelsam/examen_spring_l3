package ism.absence.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "vigiles")
public class Vigile {
    @Id
    private String id;
    private String numeroVig;
    private String userId;

}