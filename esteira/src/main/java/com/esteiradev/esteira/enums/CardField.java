package com.esteiradev.esteira.enums;

import java.util.Arrays;

public enum CardField {
    TITLE("Título"),
    DESCRIPTION("Descrição"),
    ESTIMATE_HOURS("Estimativa"),
    SPRINT("Sprint"),
    POSITION("Posição"),
    ESTEIRA("Esteira"),
    PRIORITY("Prioridade");

    private final String label;

    CardField(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static String fromFieldName(String fieldName) {
        return Arrays.stream(values())
                .filter(f -> f.name().equalsIgnoreCase(fieldName))
                .map(CardField::getLabel)
                .findFirst()
                .orElse(fieldName);
    }
}
