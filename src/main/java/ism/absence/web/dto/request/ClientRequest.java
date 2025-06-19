package ism.absence.web.dto.request;

import lombok.Data;

@Data
public class ClientRequest {
    private String id;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
}