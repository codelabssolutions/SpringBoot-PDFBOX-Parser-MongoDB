package com.codelabs.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "dados_do_paciente")
public class DadosDoPaciente  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nome;
    private String sexo;
    private String altura;
    private String peso;
    private String idade;
    private String fumante;
    private String motivoDoExame;
    @Id
	private String id;
    
    
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getFumante() {
        return fumante;
    }

    public void setFumante(String fumante) {
        this.fumante = fumante;
    }

    public String getMotivoDoExame() {
        return motivoDoExame;
    }

    public void setMotivoDoExame(String motivoDoExame) {
        this.motivoDoExame = motivoDoExame;
    }

	@Override
	public String toString() {
		return "DadosDoPaciente [nome=" + nome + ", sexo=" + sexo + ", altura=" + altura + ", peso=" + peso + ", idade="
				+ idade + ", fumante=" + fumante + ", motivoDoExame=" + motivoDoExame + "]";
	}
    
}
