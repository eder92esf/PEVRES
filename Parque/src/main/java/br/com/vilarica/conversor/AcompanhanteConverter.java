package br.com.vilarica.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.vilarica.model.Acompanhante;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.service.AcompanhanteService;

@FacesConverter(forClass = Acompanhante.class)
public class AcompanhanteConverter implements Converter{

	private @Inject AcompanhanteService controller;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && !value.equals("")){
			return this.controller.porId(new Long(value));
		}
		Acompanhante a = new Acompanhante();
		a.setMunicipio(new Municipio());
		return a;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			Acompanhante a = (Acompanhante) value;
			if(a.getId() != null)
				return a.getId().toString();
		}
		return null;
	}

}
