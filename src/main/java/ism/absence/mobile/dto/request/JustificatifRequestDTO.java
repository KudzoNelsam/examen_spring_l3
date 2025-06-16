package ism.absence.mobile.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class JustificatifRequestDTO {
    private String motif;
    private List<String> piecesJointes;
}