package ism.absence.web.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DetteResponseDTO {
    private String id;
    private LocalDate date;
    private double montantDette;
    private double montantPaye;
    private double montantRestant;
    private String numero;
}