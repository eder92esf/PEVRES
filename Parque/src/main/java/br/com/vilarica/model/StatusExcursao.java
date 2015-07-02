package br.com.vilarica.model;

import java.util.ArrayList;
import java.util.List;

public enum StatusExcursao {

	AGENDADA("Agendada"),
	REALIZADA("Realizada"),
	ADIADA("Adiada"),
	CANCELADA("Cancelada");
	
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	StatusExcursao(String status) {
		this.status = status;
	}
	
	public static List<StatusExcursao> getStatusExcursao(){
		List<StatusExcursao> status = new ArrayList<StatusExcursao>();
		for (StatusExcursao statusExcursao : StatusExcursao.values()) {
			status.add(statusExcursao);
		}
		return status;
	}
	
	public static StatusExcursao getStatus(String status){
		switch (status) {
		case "Agendada":
			return AGENDADA;
		case "Realizada":
			return REALIZADA;
		case "Adiada":
			return ADIADA;
		case "Cancelada":
			return CANCELADA;
		}
		return null;
	}
}
