package ism.absence.data.models;

import ism.absence.data.enums.EtatJustificatif;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "justificatifs")
@Data
public class Justificatif {
    @Id
    private String id;
    private String motif;
    private LocalDateTime date;
    private EtatJustificatif etat;
    private String pieceJointe;
}