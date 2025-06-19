package ism.absence.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
public class Dette {
    @Id
    private String id;
    private LocalDate date;
    private double montantDette;
    private double montantPaye;
    private double montantRestant;
    private String numero;
//
    private String clientId;
}