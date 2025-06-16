package ism.absence.mobile.dto.response;

import lombok.Data;

@Data
public class AbsenceRetardDTO {
    private String id;
    private String date;
    private String type; // "ABSENCE" ou "RETARD"
    private boolean justifie;
    private String seanceCoursId;
    private String nomCours;
    private String nomProf;
}