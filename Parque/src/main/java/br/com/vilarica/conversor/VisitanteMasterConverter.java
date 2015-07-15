package br.com.vilarica.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.vilarica.model.Contato;
import br.com.vilarica.model.Endereco;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.model.VisitanteMaster;
import br.com.vilarica.service.VisitanteMasterService;

@FacesConverter(forClass = VisitanteMaster.class)
public class VisitanteMasterConverter implements Converter{

	private @Inject VisitanteMasterService controller;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && !value.equals("")){
			return this.controller.porId(new Long(value));
		}
		
		VisitanteMaster vm = new VisitanteMaster();
		vm.setContato(new Contato());
		vm.setEndereco(new Endereco());
		vm.setMunicipio(new Municipio());
		return vm;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			VisitanteMaster vm = (VisitanteMaster) value;
			if(vm.getId() != null)
				return vm.getId().toString();
		}
		return null;
	}

}
