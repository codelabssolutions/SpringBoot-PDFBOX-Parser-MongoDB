package com.codelabs.entity;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exemplocs_deecg")
public class ExemplosDeECG implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String horario;
	private String dur;
	private String atividate;
	private String sintomas;
	private String diagnostico;
	private String basal;
	private String fc;

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getDur() {
		return dur;
	}

	public void setDur(String dur) {
		this.dur = dur;
	}

	public String getAtividate() {
		return atividate;
	}

	public void setAtividate(String atividate) {
		this.atividate = atividate;
	}

	public String getSintomas() {
		return sintomas;
	}

	public void setSintomas(String sintomas) {
		this.sintomas = sintomas;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getBasal() {
		return basal;
	}

	public void setBasal(String basal) {
		this.basal = basal;
	}

	public String getFc() {
		return fc;
	}

	public void setFc(String fc) {
		this.fc = fc;
	}

}
