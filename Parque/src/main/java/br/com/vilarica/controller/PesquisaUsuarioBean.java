package br.com.vilarica.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vilarica.model.Usuario;
import br.com.vilarica.service.UsuarioService;

@Named
@ViewScoped
public class PesquisaUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject UsuarioService controller;
	private @Inject Usuario selecionado;
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UsuarioService getController() {
		return controller;
	}
	
	public Usuario getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Usuario selecionado) {
		this.selecionado = selecionado;
	}

	public void filter(){
		this.controller.filtrar(username);
	}

	public void remover(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		String retorno = this.controller.remove(getSelecionado());
		if(retorno.equals("")){
			msg = new FacesMessage("Usuário removido com sucesso!");
		}else{
			msg = new FacesMessage("Erro ao remover usuário!");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		context.addMessage(null, msg);
	}
}
