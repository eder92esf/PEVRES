package br.com.vilarica.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.vilarica.model.Contato;
import br.com.vilarica.model.Endereco;
import br.com.vilarica.model.Instituicao;
import br.com.vilarica.service.InstituicaoService;

@FacesConverter(forClass = Instituicao.class)
public class InstituicaoConverter implements Converter{

	private @Inject InstituicaoService controller;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && !value.equals("")){
			return this.controller.porId(new Long(value));
		}
		
		Instituicao i = new Instituicao();
		i.setContato(new Contato());
		i.setEndereco(new Endereco());
		return i;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			Instituicao i = (Instituicao) value;
			if(i.getId() != null)
				return i.getId().toString();
		}
		return null;
	}

}
