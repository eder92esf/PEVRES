package br.com.vilarica.model;

import java.util.ArrayList;
import java.util.List;

public enum MeioTransporteEnum {

	ONIBUS_ESCOLAR("Ônibus Escolar"),
	ONIBUS_TURISMO("Ônibus de Turismo"),
	CARRO("Carro"),
	MOTOCICLETA("Motocicleta"),
	BICICLETA("Bicicleta"),
	A_PE("A Pé"),
	OUTRO("Outro");

	private String meioTransporte;
	
	MeioTransporteEnum(String meioTransporte){
		this.meioTransporte = meioTransporte;
	}
	
	public String getMeioTransporte() {
		return meioTransporte;
	}

	public void setMeioTransporte(String meioTransporte) {
		this.meioTransporte = meioTransporte;
	}

	public static List<MeioTransporteEnum> getMeiosTransporte(){
		List<MeioTransporteEnum> meios = new ArrayList<MeioTransporteEnum>();
		for (MeioTransporteEnum meio :MeioTransporteEnum.values()) {
			meios.add(meio);
		}
		return meios;
	}
	
	public static Enum getMeioTransporte(String esc) {
		switch (esc) {
		case "Ônibus Escolar":
			return ONIBUS_ESCOLAR;
		case "Ônibus de Turismo":
			return ONIBUS_TURISMO;
		case "Carro":
			return CARRO;
		case "Motocicleta":
			return MOTOCICLETA;
		case "Bicicleta":
			return BICICLETA;
		case "A Pé":
			return A_PE;
		case "Outro":
			return OUTRO;
		default:
			return null;
		}
	}
}
