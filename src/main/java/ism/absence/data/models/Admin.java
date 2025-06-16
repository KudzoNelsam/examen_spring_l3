package ism.absence.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "administrateurs")
public class Admin {
    @Id
    private String id;
    private String userId;
}