package com.esteiradev.esteira.enums;

public enum PriorityEnum {
    HIGH("Alta"),
    MEDIUM("Médio"),
    LOW("Baixo");

    private final String label;

    PriorityEnum(String label) {
        this.label = label;

    }

    public String getLabel() {
        return label;
    }
}
