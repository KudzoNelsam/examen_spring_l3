package ism.absence.data.models;

import ism.absence.data.enums.EtatJustificatif;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "justificatifs")
@Data
public class Justificatif {
    @Id
    private String id;
    private String motif;
    private String matricule;
    private LocalDateTime date;
    private EtatJustificatif etat;
    private List<String> piecesJointes; // Liste d'URLs Cloudinary
}