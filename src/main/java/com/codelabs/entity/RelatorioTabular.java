package com.codelabs.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "relatorio_tabular")
public class RelatorioTabular implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String hora;
	private String  fcmin;
	private String  fcmed;
	private String  fcmax;
	private String  QRSs;
	private String  viso;
	private String  vpar;
	private String  taqV;
	private String  totV;
	private String  sviso;
	private String  svpar;
	private String  taqSV;
	private String  totSV;
	private String  fausas;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getFcmin() {
		return fcmin;
	}
	public void setFcmin(String fcmin) {
		this.fcmin = fcmin;
	}
	public String getFcmed() {
		return fcmed;
	}
	public void setFcmed(String fcmed) {
		this.fcmed = fcmed;
	}
	public String getFcmax() {
		return fcmax;
	}
	public void setFcmax(String fcmax) {
		this.fcmax = fcmax;
	}
	public String getQRSs() {
		return QRSs;
	}
	public void setQRSs(String qRSs) {
		QRSs = qRSs;
	}
	public String getViso() {
		return viso;
	}
	public void setViso(String viso) {
		this.viso = viso;
	}
	public String getVpar() {
		return vpar;
	}
	public void setVpar(String vpar) {
		this.vpar = vpar;
	}
	public String getTaqV() {
		return taqV;
	}
	public void setTaqV(String taqV) {
		this.taqV = taqV;
	}
	public String getTotV() {
		return totV;
	}
	public void setTotV(String totV) {
		this.totV = totV;
	}
	public String getSviso() {
		return sviso;
	}
	public void setSviso(String sviso) {
		this.sviso = sviso;
	}
	public String getSvpar() {
		return svpar;
	}
	public void setSvpar(String svpar) {
		this.svpar = svpar;
	}
	public String getTaqSV() {
		return taqSV;
	}
	public void setTaqSV(String taqSV) {
		this.taqSV = taqSV;
	}
	public String getTotSV() {
		return totSV;
	}
	public void setTotSV(String totSV) {
		this.totSV = totSV;
	}
	public String getFausas() {
		return fausas;
	}
	public void setFausas(String fausas) {
		this.fausas = fausas;
	}
	
	
}
