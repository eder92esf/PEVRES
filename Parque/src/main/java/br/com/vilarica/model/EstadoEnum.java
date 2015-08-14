package br.com.vilarica.model;

import java.util.ArrayList;
import java.util.List;

public enum EstadoEnum {
	PR("Paraná"), //
	SC("Santa Catarina"), //
	RS("Rio Grande do Sul"), //
	MT("Mato Grosso"), // 
    MS("Mato Grosso do Sul"), // 
    MG("Minas Gerais"), //
    SP("São Paulo"), //
    RJ("Rio de Janeiro"), //
    AC("Acre"), // 
    AL("Alagoas"), // 
    AP("Amapá"), // 
    AM("Amazonas"), // 
    BA("Bahia"), // 
    CE("Ceará"), // 
    DF("Distrito Federal"), // 
    ES("Espírito Santo"), // 
    GO("Goiás"), // 
    MA("Maranhão"), // 
    PA("Pará"), // 
    PB("Paraíba"), // 
    PE("Pernambuco"), // 
    PI("Piauí"), // 
    RR("Roraima"), // 
    RO("Rondônia"), // 
    RN("Rio Grande do Norte"), // 
    SE("Sergipe"), // 
    TO("Tocantins"),
    OUTRO("Outro"); //    
    
    private String uf;
    
    private EstadoEnum(String uf){
        this.uf = uf;
    }
    
    public String getUf(){
        return this.uf;
    }
    
    public static List<EstadoEnum> getEstados(){
    	List<EstadoEnum> l = new ArrayList<EstadoEnum>();
    	for (EstadoEnum estadoEnum : EstadoEnum.values()) {
			l.add(estadoEnum);
		}
    	return l;
    }
    
    public static String[] getUfs() {  
        String[] estado = new String[EstadoEnum.values().length];  
        for (EstadoEnum estadoEnum : EstadoEnum.values()) {  
            estado[estadoEnum.ordinal()] = estadoEnum.getUf();  
        }  
        return estado ;  
    } 
    
    public static EstadoEnum getEnum(String string){
        switch (string) {
            case "Acre":
                return AC;
            case "Alagoas":
                return AL;
            case "Amapá":
                return AP;
            case "Amazonas":
                return AM;
            case "Bahia":
                return BA;
            case "Ceará":
                return CE;
            case "Distrito Federal":
                return DF;
            case "Espírito Santo":
                return ES;
            case "Goiás":
                return GO;
            case "Maranhão":
                return MA;
            case "Mato Grosso":
                return MT;
            case "Mato Grosso do Sul":
                return MS;     
            case "Minas Gerais":
                return MG;  
            case "Pará":
                return PA;
            case "Paraíba":
                return PB;
            case "Paraná":
                return PR;
            case "Pernambuco":
                return PE;
            case "Piauí":
                return PI;
            case "Roraima":
                return RR;
            case "Rondônia":
                return RO;
            case "Rio de Janeiro":
                return RJ;
            case "Rio Grande do Norte":
                return RN;
            case "Rio Grande do Sul":
                return RS;
            case "Santa Catarina":
                return SC;
            case "São Paulo":
                return SP;
            case "Sergipe":
                return SE;
            case "Tocantins":
                return TO;
            case "Outro":
            	return OUTRO;
        }
        return null;
     }  
}
