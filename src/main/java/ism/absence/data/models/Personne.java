package ism.absence.data.models;

import ism.absence.data.records.Localisation;
import lombok.Data;


@Data
public class Personne {
    private String fullName;
    private String adresse;
    private Localisation localisation;
}