package br.com.vilarica.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vilarica.model.Instituicao;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.service.InstituicaoService;

@Named
@ViewScoped
public class CadastroInstituicaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject InstituicaoService controller;
	private @Inject Instituicao instituicao;
	private Municipio oldMunicipio;

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public InstituicaoService getController() {
		return controller;
	}
	
	public List<Municipio> filtrarMunicipio(String consulta){
		oldMunicipio = this.instituicao.getMunicipio();
		return this.controller.filtarMunicipios(consulta);
	}

	public boolean isEditando(){
		if(this.instituicao != null && this.instituicao.getId() != null)
			return true;
		return false;
	}
	
	public void salvar(){
		boolean editando = isEditando();
		String retorno = this.controller.save(instituicao, oldMunicipio);
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		if(retorno.equals("")){
			if(editando)
				msg = new FacesMessage("Instituição atualizada com sucesso!");
			else
				msg = new FacesMessage("Instituição cadastrada com sucesso!");
		}else{
			if(editando)
				msg = new FacesMessage("Erro ao atualizar instituição!");
			else
				msg = new FacesMessage("Erro ao cadastrar instituição!");
			
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			msg.setDetail(retorno);
		}
		context.addMessage(null, msg);
	}
}
