package ism.absence.core.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatriculeGeneratorTest {

    @Test
    void generateMatricule() {
        System.out.println("generateMatricule");
        MatriculeGenerator matriculeGenerator = new MatriculeGenerator();
        System.out.println(matriculeGenerator.generateMatricule(20));
    }
}