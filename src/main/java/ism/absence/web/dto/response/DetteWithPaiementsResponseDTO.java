package ism.absence.web.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class DetteWithPaiementsResponseDTO {
    private DetteResponseDTO detteResponseDTO;
    private List<PaiementResponseDTO> paiementResponseDTOList;
}