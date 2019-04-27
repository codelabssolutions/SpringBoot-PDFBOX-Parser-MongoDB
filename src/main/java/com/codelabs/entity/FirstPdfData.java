package com.codelabs.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "first_pdf_data")
public class FirstPdfData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	
	private DadosDoExame dadosDoExame;
	
	private DadosDoPaciente dadosDoPaciente;
	
	private MedicoSolicitante medicoSolicitante;
	
	private ResumoEstatstico resumoEstatstico;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public DadosDoExame getDadosDoExame() {
		return dadosDoExame;
	}
	public void setDadosDoExame(DadosDoExame dadosDoExame) {
		this.dadosDoExame = dadosDoExame;
	}
	public DadosDoPaciente getDadosDoPaciente() {
		return dadosDoPaciente;
	}
	public void setDadosDoPaciente(DadosDoPaciente dadosDoPaciente) {
		this.dadosDoPaciente = dadosDoPaciente;
	}
	public MedicoSolicitante getMedicoSolicitante() {
		return medicoSolicitante;
	}
	public void setMedicoSolicitante(MedicoSolicitante medicoSolicitante) {
		this.medicoSolicitante = medicoSolicitante;
	}
	public ResumoEstatstico getResumoEstatstico() {
		return resumoEstatstico;
	}
	public void setResumoEstatstico(ResumoEstatstico resumoEstatstico) {
		this.resumoEstatstico = resumoEstatstico;
	}
	@Override
	public String toString() {
		return "FirstPdfData [id=" + id + ", dadosDoExame=" + dadosDoExame + ", dadosDoPaciente=" + dadosDoPaciente
				+ ", medicoSolicitante=" + medicoSolicitante + ", resumoEstatstico=" + resumoEstatstico + "]";
	}
	
	
	
}
