package com.esteiradev.esteira.enums;

public enum PriorityEnum {
    HIGH("Alta",1),
    MEDIUM("Médio",2),
    LOW("Baixo",3);

    private final String label;
    private final Integer ordem;

    PriorityEnum(String label, Integer ordem) {
        this.label = label;
        this.ordem = ordem;
    }

    public String getLabel() {
        return label;
    }

    public Integer getOrdem() {
        return ordem;
    }
}
