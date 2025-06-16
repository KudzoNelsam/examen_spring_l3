package ism.absence.core.dto.request;

import ism.absence.data.records.Localisation;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public final class UserRequestDTO {
    private String username;
    private String password;
    private String fullName;
    private String adresse;
    private Localisation localisation;
    private String role;
}