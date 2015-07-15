package br.com.vilarica.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vilarica.model.Guia;
import br.com.vilarica.service.GuiaService;
import br.com.vilarica.util.FilterUtil;

@Named
@ViewScoped
public class PesquisaGuiaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private @Inject FilterUtil filterUtil;
	private @Inject Guia selecionado;
	private @Inject GuiaService controller;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Guia getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Guia selecionado) {
		this.selecionado = selecionado;
	}

	public GuiaService getController() {
		return controller;
	}

	public void filter(){
		this.controller.filtarGuias(nome);
	}
}
