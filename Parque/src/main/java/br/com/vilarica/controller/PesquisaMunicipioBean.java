package br.com.vilarica.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vilarica.service.MunicipioService;

@Named
@ViewScoped
public class PesquisaMunicipioBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private @Inject MunicipioService controller;
	private String nomeMunicipio;

	public String getNomeMunicipio() {
		return nomeMunicipio;
	}

	public void setNomeMunicipio(String nomeMunicipio) {
		this.nomeMunicipio = nomeMunicipio;
	}
	
	public MunicipioService getController() {
		return controller;
	}

	public void filter(){
		this.controller.filter(nomeMunicipio, null);
	}
}
