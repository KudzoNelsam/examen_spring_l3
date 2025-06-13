package ism.absence.data.enums;

public enum Grade {
    L1("LICENCE 1"),
    L2("LICENCE 2"),
    L3("LICENCE 3"),
    M1("MASTER 1"),
    M2("MASTER 2");

    Grade(String value) {
    }

    static Grade getValue(String value){
        for(Grade grade : Grade.values()){
            if(grade.name().equalsIgnoreCase(value)){
                return grade;
            }
        }
        return null;
    }
}