package ism.absence.web.dto.response;

import lombok.Data;

@Data
public class ClientResponseDTO {
    private String id;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
}