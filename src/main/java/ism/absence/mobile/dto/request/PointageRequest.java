package ism.absence.mobile.dto.request;

import ism.absence.mobile.dto.response.SeanceCoursDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PointageRequest {
    private String passer;
    private String matricule;
    private String seanceCoursId;
    private String vigileId;
    private LocalDateTime date;
}