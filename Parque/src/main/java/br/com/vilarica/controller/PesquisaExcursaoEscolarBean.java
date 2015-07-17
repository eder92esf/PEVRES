package br.com.vilarica.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.vilarica.model.Acompanhante;
import br.com.vilarica.model.ExcursaoEscolar;
import br.com.vilarica.service.ExcursaoService;

@Named
@ViewScoped
public class PesquisaExcursaoEscolarBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private @Inject ExcursaoService controller;
	private @Inject ExcursaoEscolar excursaoEscolar;
	private Date dataExcursao;
	private List<ExcursaoEscolar> excursoes;
	private List<Acompanhante> acompanhantes;
	
	

	public ExcursaoService getController() {
		return controller;
	}

	public ExcursaoEscolar getExcursaoEscolar() {
		return excursaoEscolar;
	}

	public void setExcursaoEscolar(ExcursaoEscolar excursaoEscolar) {
		this.excursaoEscolar = excursaoEscolar;
		acompanhantes = excursaoEscolar.getAcompanhantes();
	}

	public Date getDataExcursao() {
		return dataExcursao;
	}

	public void setDataExcursao(Date dataExcursao) {
		this.dataExcursao = dataExcursao;
	}
	
	public List<ExcursaoEscolar> getExcursoes() {
		return excursoes;
	}
	
	public List<Acompanhante> getAcompanhantes(){
		return excursaoEscolar.getAcompanhantes();
	}

	public void onDateSelect(SelectEvent event){
		excursoes = controller.listExcursaoEscolar(dataExcursao);
	}
}
