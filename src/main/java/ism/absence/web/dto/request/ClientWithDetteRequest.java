package ism.absence.web.dto.request;

import ism.absence.core.validator.annotations.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ClientWithDetteRequest {
    private String clientId;
    @NotEmpty(message = "Le client doit avoir au moins une commande")
    private List<DetteRequest> dettes;
}