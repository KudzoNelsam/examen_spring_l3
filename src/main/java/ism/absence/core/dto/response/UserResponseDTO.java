package ism.absence.core.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponseDTO {
    private String token;
    private String username;
    private String role;
}