package ism.absence.mobile.dto.response;

import lombok.Data;

@Data
public class EtudiantResponseDTO {
    private String fullName;
    private String matricule;
    private String photoUrl;
    private String qrCodeContent;
    private String motCle; // Passer
    private boolean aJour = true; // Avec la scolarité
    private SeanceCoursDTO currentSeanceCours;
}