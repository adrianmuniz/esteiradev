package com.esteiradev.esteira.enums;

public enum PriorityEnum {
    HIGH(1,"Alta"),
    MEDIUM(2,"Médio"),
    LOW(3,"Baixo");

    private final Integer ordem;
    private final String label;

    PriorityEnum(Integer ordem, String label) {
        this.ordem = ordem;
        this.label = label;

    }

    public String getLabel() {
        return label;
    }

    public Integer getOrdem() {
        return ordem;
    }
}
