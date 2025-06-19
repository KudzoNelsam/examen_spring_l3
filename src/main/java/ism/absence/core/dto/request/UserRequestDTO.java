package ism.absence.core.dto.request;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public final class UserRequestDTO {
    private String username;
    private String password;
    private String fullName;
    private String adresse;
    private String role;
}