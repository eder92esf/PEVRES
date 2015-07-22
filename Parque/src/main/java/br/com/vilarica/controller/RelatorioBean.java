package br.com.vilarica.controller;

import java.io.Serializable;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vilarica.service.RelatorioService;

@Named
@ViewScoped
public class RelatorioBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private @Inject RelatorioService controller;
	private Date inicio;
	private Date fim;
	
	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public void gerar(){
		this.controller.gerarRelatorio(getInicio(), getFim());
	}
}
