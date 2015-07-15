package br.com.vilarica.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.vilarica.model.Contato;
import br.com.vilarica.model.Endereco;
import br.com.vilarica.model.Guia;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.service.GuiaService;

@FacesConverter(forClass = Guia.class)
public class GuiaConverter implements Converter{

	private @Inject GuiaService controller;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && !value.equals("")){
			return this.controller.porId(new Long(value));
		}
		
		Guia g = new Guia();
		g.setContato(new Contato());
		g.setEndereco(new Endereco());
		g.setMunicipio(new Municipio());
		return g;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			Guia g = (Guia) value;
			if(g.getId() != null)
				return g.getId().toString();
		}
		return null;
	}

}
