package br.com.vilarica.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.vilarica.model.ExcursaoEscolar;
import br.com.vilarica.model.Guia;
import br.com.vilarica.model.Instituicao;
import br.com.vilarica.service.ExcursaoService;

@FacesConverter(forClass = ExcursaoEscolar.class)
public class ExcursaoEscolarConverter implements Converter{

	private @Inject ExcursaoService controller;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && !value.equals("")){
			return this.controller.excursaoEscolarPorId(new Long(value));
		}
		
		ExcursaoEscolar ee = new ExcursaoEscolar();
		ee.setGuia(new Guia());
		ee.setInstituicao(new Instituicao());
		return ee;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			ExcursaoEscolar ee = (ExcursaoEscolar) value;
			if(ee.getId() != null)
				return ee.getId().toString();
		}
		return null;
	}

}
