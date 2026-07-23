package com.atividade3.clinica.entities;

public class Prontuario {

    private int codigo;
    private int consultaCodigo; // Liga o prontuário diretamente a uma Consulta
    private String descricao;
    private String observacao;

    public Prontuario() {
    }

    public Prontuario(int codigo, int consultaCodigo, String descricao, String observacao) {
        this.codigo = codigo;
        this.consultaCodigo = consultaCodigo;
        this.descricao = descricao;
        this.observacao = observacao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getConsultaCodigo() {
        return consultaCodigo;
    }

    public void setConsultaCodigo(int consultaCodigo) {
        this.consultaCodigo = consultaCodigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}