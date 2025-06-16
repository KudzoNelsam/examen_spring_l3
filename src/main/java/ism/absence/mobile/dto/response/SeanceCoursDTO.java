package ism.absence.mobile.dto.response;

import ism.absence.data.enums.StatutPointage;
import ism.absence.data.records.Localisation;
import lombok.Data;

import java.time.LocalTime;

@Data
public class SeanceCoursDTO {
   private String nomCours;
   private LocalTime heureDebut;
   private LocalTime heureFin;
   private String professeur;
   private Localisation localisation;
   private boolean estPointe = false;
   private StatutPointage statutPointage;
}