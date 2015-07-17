package br.com.vilarica.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.vilarica.model.Acompanhante;
import br.com.vilarica.model.ExcursaoTuristica;
import br.com.vilarica.service.ExcursaoService;

@Named
@ViewScoped
public class PesquisaExcursaoTuristicaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject ExcursaoService controller;
	private @Inject ExcursaoTuristica excursaoTuristica;
	private Date dataExcursao;
	private List<ExcursaoTuristica> excursoes;
	private List<Acompanhante> acompanhantes;

	public ExcursaoService getController() {
		return controller;
	}

	public ExcursaoTuristica getExcursaoTuristica() {
		return excursaoTuristica;
	}

	public void setExcursaoTuristica(ExcursaoTuristica excursaoTuristica) {
		this.excursaoTuristica = excursaoTuristica;
		acompanhantes = excursaoTuristica.getAcompanhantes();
	}

	public Date getDataExcursao() {
		return dataExcursao;
	}

	public void setDataExcursao(Date dataExcursao) {
		this.dataExcursao = dataExcursao;
	}

	public List<ExcursaoTuristica> getExcursoes() {
		return excursoes;
	}

	public List<Acompanhante> getAcompanhantes() {
		return this.excursaoTuristica.getAcompanhantes();
	}

	public void onDateSelect(SelectEvent event) {
		excursoes = controller.listExcursaoTuristica(dataExcursao);
	}
}
