package ism.absence.core.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MatriculeGenerator {

    public String generateMatricule(int numero) {
        int size = 5;
        // ISM2223/DK-30355
        //ISM+Year-1+Year/Ville-numero

        return "ISM"
                + String.valueOf(LocalDate.now().getYear()).substring(2)
                + String.valueOf(LocalDate.now().getYear() - 1).substring(2)
                + "-DK-"
                + "0".repeat(size - String.valueOf(numero).length())
                + numero;
    }

}