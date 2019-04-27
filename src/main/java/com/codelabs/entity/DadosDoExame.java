package com.codelabs.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dados_do_exame")
public class DadosDoExame implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
    private String nDOExame;
    private String dataDoExame;
    private String protocolo;
    private String codigo;
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getnDOExame() {
		return nDOExame;
	}
	public void setnDOExame(String nDOExame) {
		this.nDOExame = nDOExame;
	}
	public String getDataDoExame() {
		return dataDoExame;
	}
	public void setDataDoExame(String dataDoExame) {
		this.dataDoExame = dataDoExame;
	}
	public String getProtocolo() {
		return protocolo;
	}
	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	@Override
	public String toString() {
		return "DadosDoExame [nDOExame=" + nDOExame + ", dataDoExame=" + dataDoExame + ", protocolo=" + protocolo
				+ ", codigo=" + codigo + "]";
	}
    
    
    

}
