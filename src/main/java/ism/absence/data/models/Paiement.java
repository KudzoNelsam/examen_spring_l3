package ism.absence.data.models;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
public class Paiement {
    @Id
    private String id;
    private LocalDate date;
    private double montant;
//
    private String detteId;
    private String clientId;
}