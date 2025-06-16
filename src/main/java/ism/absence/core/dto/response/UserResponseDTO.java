package ism.absence.core.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(
        name = "UserResponseDTO",
        description = "Représente la réponse d'authentification, contenant les tokens JWT et les informations de l'utilisateur."
)
public class UserResponseDTO {
    private String token;
    private String username;
    private String role;

}