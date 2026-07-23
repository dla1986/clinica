package com.atividade3.clinica.entities;

public class Receituario {

    private int codigo;
    private int prontuarioCodigo;
    private String observacao;

    public Receituario() {
    }

    public Receituario(int codigo, int prontuarioCodigo, String observacao) {
        this.codigo = codigo;
        this.prontuarioCodigo = prontuarioCodigo;
        this.observacao = observacao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getProntuarioCodigo() {
        return prontuarioCodigo;
    }

    public void setProntuarioCodigo(int prontuarioCodigo) {
        this.prontuarioCodigo = prontuarioCodigo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}