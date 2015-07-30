package br.com.vilarica.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.vilarica.model.Grupo;
import br.com.vilarica.service.GrupoService;

@FacesConverter(forClass = Grupo.class)
public class GrupoConverter implements Converter{

	private @Inject GrupoService controller;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && !value.equals("")){
			return this.controller.porId(new Long(value));
		}
		Grupo g = new Grupo();
		return g;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			Grupo g = (Grupo) value;
			if(g.getId() != null)
				return g.getId().toString();
		}
		return null;
	}

}
