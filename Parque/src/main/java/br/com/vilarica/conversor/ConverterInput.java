package br.com.vilarica.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "inputMaskConverter")
public class ConverterInput implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		if(valor != null && !valor.equals("")){
			StringBuilder sb = new StringBuilder(valor);
			sb.toString().replaceAll("[- /.]", "");  
            sb.toString().replaceAll("[-()]", "");
            return sb.toString();
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object valor) {
		if(valor != null)
			return valor.toString();
		return null;
	}

}
