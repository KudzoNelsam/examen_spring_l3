package ism.absence.web.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaiementResponseDTO {
    private String detteId;
    private LocalDate localDate;
    private double montant;
    private double montantRestant;
}