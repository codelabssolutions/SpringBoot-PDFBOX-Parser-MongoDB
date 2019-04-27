package com.codelabs.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "laudo_medico")
public class LaudoMedico implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String alterMorDeSt;
	private String compoClin;
	private String coment;
	private String conclu;
	private String refer;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlterMorDeSt() {
		return alterMorDeSt;
	}

	public void setAlterMorDeSt(String alterMorDeSt) {
		this.alterMorDeSt = alterMorDeSt;
	}

	public String getCompoClin() {
		return compoClin;
	}

	public void setCompoClin(String compoClin) {
		this.compoClin = compoClin;
	}

	public String getComent() {
		return coment;
	}

	public void setComent(String coment) {
		this.coment = coment;
	}

	public String getConclu() {
		return conclu;
	}

	public void setConclu(String conclu) {
		this.conclu = conclu;
	}

	public String getRefer() {
		return refer;
	}

	public void setRefer(String refer) {
		this.refer = refer;
	}

}
