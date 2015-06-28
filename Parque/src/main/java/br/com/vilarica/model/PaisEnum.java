package br.com.vilarica.model;

import java.util.ArrayList;
import java.util.List;

public enum PaisEnum {
    BRASIL("Brasil"),
    PARAGUAI("Paraguai"),
    ARGENTINA("Argentina"),
    URUGUAI("Uruguai"),
    BOLIVIA("Bolívia"),
    EUA("Estados Unidos"),
    OUTRO("Outro"); //    
    
    private String nome;
    
    private PaisEnum(String nome){
        this.nome = nome;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public static List<PaisEnum> getPaises(){
    	List<PaisEnum> l = new ArrayList<PaisEnum>();
    	for (PaisEnum paisEnum : PaisEnum.values()) {
			l.add(paisEnum);
		}
    	return l;
    }
    
    public static PaisEnum getEnum(String string){
        switch (string) {
            case "Brasil":
                return BRASIL;
            case "Paraguai":
                return PARAGUAI;
            case "Argentina":
                return ARGENTINA;
            case "Uruguai":
                return URUGUAI;
            case "Bolivía":
                return BOLIVIA;
            case "Estados Unidos":
                return EUA;
            case "Outro":
                return OUTRO;
        }
        return null;
     }
}
