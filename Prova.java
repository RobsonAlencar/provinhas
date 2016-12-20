package br.gov.serpro.provinha.model;

import java.io.Serializable;

/**
 * Created by 82728925534 on 27/09/16.
 */

public class Prova implements Serializable {
    public String nome;
    public String disciplina;

    public Prova(String nome, String disciplina) {
        this.nome = nome;
        this.disciplina = disciplina;
    }

}
