package com.esteiradev.esteira.enums;

public enum StatusCard {
    TODO("TO-DO",1),
    ANALISE("Análise", 2),
    DEV("Desenvolvimento", 3),
    TEST("Testando", 4),
    BUILD_DEPLOY("Build e Deploy", 5),
    FECHADO("Fechado", 6);

    private final String nome;
    private final int posicao;

    StatusCard(String nome, int posicao) {
        this.nome = nome;
        this.posicao = posicao;
    }

    public String getNome() {
        return nome;
    }

    public int getPosicao() {
        return posicao;
    }
}
