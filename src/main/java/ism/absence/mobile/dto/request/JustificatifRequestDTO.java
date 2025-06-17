package ism.absence.mobile.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;


@Data
public class JustificatifRequestDTO {

    @NotBlank(message = "Le matricule est obligatoire")
    private String matricule;

    @NotBlank(message = "Le motif est obligatoire")
    @Size(min = 10, max = 500, message = "Le motif doit contenir entre 10 et 500 caractères")
    private String motif;

    private List<String> piecesJointes; // URLs Cloudinary

    private String pointageId; // Optionnel, pour lier à un pointage spécifique
}