package com.atividade3.clinica.entities;

public class ItemReceituario {

    private int codigo;
    private int receituarioCodigo;
    private int medicamentoCodigo;
    private int dosagem;
    private int intervaloEntreDoses;
    private String observacao;
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getReceituarioCodigo() {
		return receituarioCodigo;
	}
	public void setReceituarioCodigo(int receituarioCodigo) {
		this.receituarioCodigo = receituarioCodigo;
	}
	public int getMedicamentoCodigo() {
		return medicamentoCodigo;
	}
	public void setMedicamentoCodigo(int medicamentoCodigo) {
		this.medicamentoCodigo = medicamentoCodigo;
	}
	public int getDosagem() {
		return dosagem;
	}
	public void setDosagem(int dosagem) {
		this.dosagem = dosagem;
	}
	public int getIntervaloEntreDoses() {
		return intervaloEntreDoses;
	}
	public void setIntervaloEntreDoses(int intervaloEntreDoses) {
		this.intervaloEntreDoses = intervaloEntreDoses;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

    
}