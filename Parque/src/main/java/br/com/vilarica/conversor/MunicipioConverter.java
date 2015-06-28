package br.com.vilarica.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.vilarica.model.Municipio;
import br.com.vilarica.service.MunicipioService;

@FacesConverter(forClass = Municipio.class)
public class MunicipioConverter implements Converter{

	private @Inject MunicipioService controller;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && !value.equals(""))
			return this.controller.porId(new Long(value));
		return new Municipio();
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			Municipio m = (Municipio) value;
			//return m.getId() != null ? m.getId().toString() : null;
			if(m.getId() != null)
				return m.getId().toString();
		}
		return null;
	}

}
