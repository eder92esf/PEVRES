package br.com.vilarica.model;

import java.util.ArrayList;
import java.util.List;

public enum SexoEnum {

	MASCULINO("Masculino"), 
	FEMININO("Feminino");
	
	SexoEnum(String sexo) {
		this.sexo = sexo;
	}
	
	private String sexo;

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public static List<SexoEnum> getSexos(){
		List<SexoEnum> sexos = new ArrayList<SexoEnum>();
		for (SexoEnum sexoEnum : SexoEnum.values()) {
			sexos.add(sexoEnum);
		}
		return sexos;
	}
	
	public static SexoEnum getEnum(String string){
		switch (string) {
		case "Masculino":
			return MASCULINO;
		case "Feminino":
			return FEMININO;
		}
		return null;
	}
}
