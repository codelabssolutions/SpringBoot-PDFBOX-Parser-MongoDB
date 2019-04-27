package com.codelabs.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "resumo_estatstico")
public class ResumoEstatstico implements Serializable{
	
	    private static final long serialVersionUID = 1L;
		@Id
		private String id;
	   // private String FC120;
	  // private String FC50;
	    private ArritmiasSupraventriculares arritmiasSupraventriculares;
	    private ArritmiasVentriculares arritmiasVentriculares;
	    private FrequenciaCardiaca frequenciaCardiaca;
	    private Totais totais;
	    private DepressaoDoST depressaoDoST;
	    private Pausas pausas;
	    private ElevacaoDoST elevacaoDoST;
	   
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		
		public Totais getTotais() {
			return totais;
		}
		public void setTotais(Totais totais) {
			this.totais = totais;
		}
				
		public FrequenciaCardiaca getFrequenciaCardiaca() {
			return frequenciaCardiaca;
		}
		public void setFrequenciaCardiaca(FrequenciaCardiaca frequenciaCardiaca) {
			this.frequenciaCardiaca = frequenciaCardiaca;
		}

	/*
	 * public String getFC120() { return FC120; } public void setFC120(String fC120)
	 * { FC120 = fC120; } public String getFC50() { return FC50; } public void
	 * setFC50(String fC50) { FC50 = fC50; }
	 */
		public ArritmiasVentriculares getArritmiasVentriculares() {
			return arritmiasVentriculares;
		}
		public void setArritmiasVentriculares(ArritmiasVentriculares arritmiasVentriculares) {
			this.arritmiasVentriculares = arritmiasVentriculares;
		}
		public ArritmiasSupraventriculares getArritmiasSupraventriculares() {
			return arritmiasSupraventriculares;
		}
		public void setArritmiasSupraventriculares(ArritmiasSupraventriculares arritmiasSupraventriculares) {
			this.arritmiasSupraventriculares = arritmiasSupraventriculares;
		}
		public DepressaoDoST getDepressaoDoST() {
			return depressaoDoST;
		}
		public void setDepressaoDoST(DepressaoDoST depressaoDoST) {
			this.depressaoDoST = depressaoDoST;
		}
		public Pausas getPausas() {
			return pausas;
		}
		public void setPausas(Pausas pausas) {
			this.pausas = pausas;
		}
		public ElevacaoDoST getElevacaoDoST() {
			return elevacaoDoST;
		}
		public void setElevacaoDoST(ElevacaoDoST elevacaoDoST) {
			this.elevacaoDoST = elevacaoDoST;
		}
		
		

}

