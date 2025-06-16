package ism.absence.mobile.dto.response;

import ism.absence.data.enums.EtatJustificatif;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class JustificatifResponseDTO {
    private String id;
    private String motif;
    private LocalDateTime date;
    private EtatJustificatif etat;
    private List<String> piecesJointes;
}