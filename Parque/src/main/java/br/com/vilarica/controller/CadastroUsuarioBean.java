package br.com.vilarica.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vilarica.model.Grupo;
import br.com.vilarica.model.Usuario;
import br.com.vilarica.service.UsuarioService;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject UsuarioService controller;
	private @Inject Usuario usuario;
	private String confirmaSenha;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioService getController() {
		return controller;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public boolean isEditando() {
		if (usuario.getId() == null)
			return false;
		return true;

	}

	public List<Grupo> getGrupos() {
		return this.controller.filtrarGrupos();
	}
	
	public void salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		if (usuario.getSenha().equals(confirmaSenha)) {
			boolean editando = isEditando();
			String retorno = this.controller.saveOrUpdate(usuario);
			if (retorno.equals("")) {
				if (!editando)
					msg = new FacesMessage("Usuário cadastrado com sucesso!");
				else
					msg = new FacesMessage("Usuário atualizado com sucesso!");
			} else {
				if (!editando)
					msg = new FacesMessage("Erro ao cadastrar usuário!");
				else
					msg = new FacesMessage("Erro ao atualizar usuário!");

				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			}
			usuario = new Usuario();
		} else {
			msg = new FacesMessage("Senha  informada não confere");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		context.addMessage(null, msg);
	}
}
