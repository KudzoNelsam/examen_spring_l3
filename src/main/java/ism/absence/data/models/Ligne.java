package ism.absence.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Ligne {
    @Id
    private String id;
    private int qteCom;
//    Relations
    private String articleId;
    private String detteId;
}