package com.esteiradev.esteira.enums;

public enum EsteiraType {
    BACKLOG(1),
    ANALYSIS(2),
    DEV(3),
    TEST(4),
    CLOSED(5),
    OUTHERS(6);

    private final int ordem;
    EsteiraType(int ordem) {
        this.ordem = ordem;
    }

    public int getOrdem() {
        return ordem;
    }
}
