package com.esteiradev.esteira.enums;

public enum CardField {
    TITLE("Título"),
    DESCRIPTION("Descrição"),
    ESTIMATE_HOURS("Estimativa (horas)"),
    SPRINT("Sprint"),
    POSITION("Posição"),
    ESTEIRA("Esteira");

    private final String label;

    CardField(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static CardField from(String fieldName) {
        return CardField.valueOf(fieldName.toUpperCase());
    }
}
