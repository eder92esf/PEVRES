package br.com.vilarica.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.vilarica.model.Acompanhante;
import br.com.vilarica.model.Excursao;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.service.AgendaExcursaoService;

@Named
@ViewScoped
public class AgendaExcursaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject Excursao excursao;
	private @Inject Acompanhante acompanhante;
	private @Inject Acompanhante selecionado;
	private @Inject AgendaExcursaoService controller;
	
	public Excursao getExcursao() {
		return excursao;
	}

	public void setExcursao(Excursao excursao) {
		this.excursao = excursao;
	}

	public AgendaExcursaoService getController() {
		return controller;
	}
	
	public Acompanhante getAcompanhante() {
		return acompanhante;
	}

	public void setAcompanhante(Acompanhante acompanhante) {
		this.acompanhante = acompanhante;
	}

	public Acompanhante getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Acompanhante selecionado) {
		this.selecionado = selecionado;
	}

	public List<Municipio> filtrarMunicipio(String consulta){
		return this.controller.filtarMunicipios(consulta);
	}
	
	public void onDateSelect(SelectEvent event) {
		this.controller.checaImputavel(this.excursao.getVisitanteMaster().getDataNascimento());
	}
	
	public void addAcompanhante(){
		this.excursao.getAcompanhantes().add(acompanhante);
		acompanhante = new Acompanhante();
		System.out.println("\nTAMANHO DA LISTA "+excursao.getAcompanhantes().size());
	}
	
	public void removeAcompanhante(){
		for (Acompanhante a : this.excursao.getAcompanhantes()) {
			if(a.equals(selecionado)){
				this.excursao.getAcompanhantes().remove(a);
				break;
			}
		}
	}
	
	public void checkin(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		String retorno = this.controller.checkinExcursao(excursao);
		
		if(retorno.equals("")){
			msg = new FacesMessage("Check-in de excursão efetuado com sucesso.");
		}else{
			msg = new FacesMessage("Erro ao efetuar check-in de excursão.");
			msg.setDetail(retorno);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		
		context.addMessage(null, msg);
	}
	
}
