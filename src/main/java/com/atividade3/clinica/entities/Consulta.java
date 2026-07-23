package com.atividade3.clinica.entities;

public class Consulta {

	private int codigo;
	private String pacienteCpf;
	private String medicoCrm;
	private String dataHora;
	private String dataHoraVolta;
	private String observacao;

	private Prontuario prontuario;
	public Consulta() {
	}

	public Consulta(int codigo, String pacienteCpf, String medicoCrm, String dataHora, String dataHoraVolta,
			String observacao) {
		this.codigo = codigo;
		this.pacienteCpf = pacienteCpf;
		this.medicoCrm = medicoCrm;
		this.dataHora = dataHora;
		this.dataHoraVolta = dataHoraVolta;
		this.observacao = observacao;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getPacienteCpf() {
		return pacienteCpf;
	}

	public void setPacienteCpf(String pacienteCpf) {
		this.pacienteCpf = pacienteCpf;
	}

	public String getMedicoCrm() {
		return medicoCrm;
	}

	public void setMedicoCrm(String medicoCrm) {
		this.medicoCrm = medicoCrm;
	}

	public String getDataHora() {
		return dataHora;
	}

	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

	public String getDataHoraVolta() {
		return dataHoraVolta;
	}

	public void setDataHoraVolta(String dataHoraVolta) {
		this.dataHoraVolta = dataHoraVolta;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Prontuario getProntuario() {
        return prontuario;
    }

    public void setProntuario(Prontuario prontuario) {
        this.prontuario = prontuario;
    }
}
