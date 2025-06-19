package ism.absence.web.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ClientWithDetteResponse {
    private ClientResponseDTO client;
    private List<DetteResponseDTO> dettes;
}