package br.com.vilarica.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vilarica.model.ExcursaoEscolar;
import br.com.vilarica.service.EmailAnexoService;

@Named
@ViewScoped
public class EnviaEmailBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private @Inject ExcursaoEscolar selecionado;
	private @Inject EmailAnexoService controller;

	public ExcursaoEscolar getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(ExcursaoEscolar selecionado) {
		this.selecionado = selecionado;
	}
	
	public void enviar(){
		String retorno = this.controller.sendEmail(selecionado);
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		if(retorno.equals("")){
			msg = new FacesMessage("E-mail enviado com sucesso!");
		}else{
			msg = new FacesMessage("Erro ao enviar e-mail!");
			msg.setDetail(retorno);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		
		context.addMessage(null, msg);
	}
	
	public boolean isEnviavel(){
		if(selecionado != null && selecionado.getId() != null)
			return true;
		return false;
	}
}
