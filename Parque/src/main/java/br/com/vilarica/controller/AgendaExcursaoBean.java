package br.com.vilarica.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vilarica.model.Excursao;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.service.AgendaExcursaoService;

@Named
@ViewScoped
public class AgendaExcursaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject Excursao excursao;
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
	
	public List<Municipio> filtrarMunicipio(String consulta){
		return this.controller.filtarMunicipios(consulta);
	}
	
	public void checkin(){
		this.controller.checkinExcursao(excursao);
	}
	
}
