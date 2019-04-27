package com.codelabs.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "medico_solicitante")
public class MedicoSolicitante implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String nome;
    private String clnica;
    private String tel;
    private String fax;
   
   
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

	public String getClnica() {
		return clnica;
	}

	public void setClnica(String clnica) {
		this.clnica = clnica;
	}

	@Override
	public String toString() {
		return "MedicoSolicitante [nome=" + nome + ", clnica=" + clnica + ", tel=" + tel + ", fax=" + fax + "]";
	}
    
}
