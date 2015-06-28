package br.com.vilarica.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vilarica.model.Municipio;
import br.com.vilarica.service.MunicipioService;

@Named
@ViewScoped
public class CadastroMunicipioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject Municipio municipio;
	private @Inject MunicipioService controller;

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public MunicipioService getController() {
		return controller;
	}
	
	public boolean isEditando(){
		if(municipio != null && municipio.getId() != null)
			return true;
		return false;
	}
	
	public void salvar(){
		boolean editando = isEditando();
		String retorno = this.controller.save(municipio);
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		if(retorno.equals("")){
			if(editando)
				msg = new FacesMessage("Município atualizado com sucesso!");
			else
				msg = new FacesMessage("Município cadastrado com sucesso!");
		}else{
			if(editando)
				msg = new FacesMessage("Erro ao atualizar Município!");
			else
				msg = new FacesMessage("Erro ao cadastrar Município!");
			
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			msg.setDetail(retorno);
		}
		context.addMessage(null, msg);
	}

}
