package br.com.vilarica.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vilarica.model.Contato;
import br.com.vilarica.model.Endereco;
import br.com.vilarica.model.Guia;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.service.GuiaService;

@Named
@ViewScoped
public class CadastroGuiaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject Guia guia;
	private @Inject GuiaService controller;

	public Guia getGuia() {
		return guia;
	}

	public void setGuia(Guia guia) {
		this.guia = guia;
	}

	public GuiaService getController() {
		return controller;
	}
	
	public boolean isEditando(){
		if(guia != null && guia.getId() != null)
			return true;
		return false;
	}
	
	public void salvar(){
		boolean editando = isEditando();
		String retorno = this.controller.save(guia);
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		if(retorno.equals("")){
			if(editando)
				msg = new FacesMessage("Guia cadastrado com sucesso!");
			else
				msg = new FacesMessage("Guia editado com sucesso!");
			
				msg.setDetail("");
		}else{
			if(editando)
				msg = new FacesMessage("Erro ao cadastrar guia!");
			else
				msg = new FacesMessage("Erro ao editar guia!");
			
			msg.setDetail(retorno);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		guia = new Guia();
		guia.setContato(new Contato());
		guia.setEndereco(new Endereco());
		guia.setMunicipio(new Municipio());
	}
	
	public List<Municipio> filtarMunicipios(String consulta){
		return this.controller.filtarMunicipios(consulta);
	}
}
