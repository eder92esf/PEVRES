package br.com.vilarica.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.vilarica.model.Usuario;
import br.com.vilarica.service.UsuarioService;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter{

	private @Inject UsuarioService controller;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && !value.equals("")){
			return this.controller.porId(new Long(value));
		}
		Usuario u = new Usuario();
		return u;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			Usuario u = (Usuario) value;
			if(u.getId() != null)
				return u.getId().toString();
		}
		return null;
	}

}
