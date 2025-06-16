package ism.absence.mobile.dto.request;

import ism.absence.core.dto.request.UserRequestDTO;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EtudiantRequestDTO {
    private UserRequestDTO userRequest;
}