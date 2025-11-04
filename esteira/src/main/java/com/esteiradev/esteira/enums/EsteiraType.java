package com.esteiradev.esteira.enums;

public enum EsteiraType {
    BACKLOG(1),
    ANALISE(2),
    DEV(3),
    TESTE(4),
    QA(5),
    RESOLVIDO(6),
    OUTROS(7);

    private final int ordem;
    EsteiraType(int ordem) {
        this.ordem = ordem;
    }

    public int getOrdem() {
        return ordem;
    }
}
