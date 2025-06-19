package ism.absence.web.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaiementRequest {
    private String detteId;
    private LocalDate  localDate;
    private double montant;
    private String clientId;
}