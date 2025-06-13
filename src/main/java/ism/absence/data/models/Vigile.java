package ism.absence.data.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "vigiles")
public class Vigile extends User{
//    @Id
//    private String vigileId;
    private String numeroVig;
}