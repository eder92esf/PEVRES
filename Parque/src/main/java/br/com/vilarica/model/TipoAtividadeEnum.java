package br.com.vilarica.model;

import java.util.ArrayList;
import java.util.List;

public enum TipoAtividadeEnum {

	VIDEO("Vídeo"),
	MUSEU("Museu"),
	TRILHA_LAGO("Trilha do Lago");
	
	private String atividade;
	
	TipoAtividadeEnum(String atividade) {
		this.setAtividade(atividade);
	}

	public String getAtividade() {
		return atividade;
	}

	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}
	
	public static List<TipoAtividadeEnum> getTipoAtividades(){
		List<TipoAtividadeEnum> tipo = new ArrayList<TipoAtividadeEnum>();
		for (TipoAtividadeEnum tipoAtividade : TipoAtividadeEnum.values()) {
			tipo.add(tipoAtividade);
		}
		return tipo;
	}
	
    public static TipoAtividadeEnum getEnum(String string){
        switch (string) {
            case "Vídeo":
                return VIDEO;
            case "Museu":
                return MUSEU;
            case "Trilha do Lago":
                return TRILHA_LAGO;
        }
        return null;
     }
}
