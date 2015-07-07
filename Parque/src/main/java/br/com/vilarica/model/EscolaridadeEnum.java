package br.com.vilarica.model;

import java.util.ArrayList;
import java.util.List;

public enum EscolaridadeEnum {

	SEM_ESCOLARIDADE("Sem Escolaridade"), EDUCACAO_INFANTIL("Educação Infantil"), ENSINO_FUNDAMENTAL_COMPLETO(
			"Ensino Fundamental Completo"), ENSINO_FUNDAMENTAL_INCOMPLETO(
			"Ensino Fundamental Incompleto"), ENSINO_MEDIO_COMPLETO(
			"Ensino Médio Completo"), ENSINO_MEDIO_INCOMPLETO(
			"Ensino Médio Incompleto"), ENSINO_SUPERIOR_COMPLETO(
			"Ensino Superior Completo"), ENSINO_SUPERIOR_INCOMPLETO(
			"Ensino Superior Incompleto");

	EscolaridadeEnum(String escolaridade){
		this.escolaridade = escolaridade;
	}
	
	private String escolaridade;

	public String getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(String escolaridade) {
		this.escolaridade = escolaridade;
	}

	public static List<EscolaridadeEnum> getEscolaridades() {
		List<EscolaridadeEnum> escolaridades = new ArrayList<EscolaridadeEnum>();
		for (EscolaridadeEnum escolaridadeEnum : EscolaridadeEnum.values()) {
			escolaridades.add(escolaridadeEnum);
		}
		return escolaridades;
	}

	public static EscolaridadeEnum getEscolaridade(String esc) {
		switch (esc) {
		case "Sem Escolaridade":
			return SEM_ESCOLARIDADE;
		case "Educaçãoo Infantil":
			return EDUCACAO_INFANTIL;
		case "Ensino Fundamental Completo":
			return ENSINO_FUNDAMENTAL_COMPLETO;
		case "Ensino Fundamental Incompleto":
			return ENSINO_FUNDAMENTAL_INCOMPLETO;
		case "Ensino Médio Completo":
			return ENSINO_MEDIO_COMPLETO;
		case "Ensino Médio Incompleto":
			return ENSINO_MEDIO_INCOMPLETO;
		case "Ensino Superior Completo":
			return ENSINO_SUPERIOR_COMPLETO;
		case "Ensino Superior Incompleto":
			return ENSINO_SUPERIOR_INCOMPLETO;
		default:
			return null;
		}
	}
}
