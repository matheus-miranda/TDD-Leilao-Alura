package br.com.msmlabs.tdd_leilao.model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private final String nome;

    public Usuario(String nome) {
        this.nome = nome;
    }

}