package ism.absence.data.enums;

public enum TypeSeance {
    PRESENTIEL("Présentiel"),
    EN_LINE("En ligne");

    TypeSeance(String value){
    }

    static TypeSeance getValue(String value){
        for(TypeSeance typeSeance : TypeSeance.values()){
            if(typeSeance.name().equalsIgnoreCase(value)){
                return typeSeance;
            }
        }
        return null;
    }
}