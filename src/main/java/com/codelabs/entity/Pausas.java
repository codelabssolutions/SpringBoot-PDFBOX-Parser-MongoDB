package com.codelabs.entity;

import org.springframework.data.mongodb.core.mapping.Document;
@Document
public class Pausas {

private String pausas;

public String getPausas() {
	return pausas;
}

public void setPausas(String pausas) {
	this.pausas = pausas;
}

}
