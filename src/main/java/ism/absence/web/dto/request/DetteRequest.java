package ism.absence.web.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DetteRequest {
    private LocalDate date;
    private double montantTotal;
    List<LigneRequest> ligneRequests;
}