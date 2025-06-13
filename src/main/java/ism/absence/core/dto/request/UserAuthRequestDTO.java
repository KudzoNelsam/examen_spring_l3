package ism.absence.core.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuthRequestDTO {
    private String username;
    private String password;
}