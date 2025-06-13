package ism.absence.data.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "administrateurs")
public class Admin extends User{
//    @Id
//    private String adminId;
}