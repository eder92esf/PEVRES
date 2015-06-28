package br.com.vilarica.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vilarica.model.EstadoEnum;
import br.com.vilarica.service.InstituicaoService;

@Named
@ViewScoped
public class PesquisaInstituicaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject InstituicaoService controller;
	private String nomeInstituicao;
	private List<EstadoEnum> estados;
	private EstadoEnum estadoSelecionado;
	
	@PostConstruct
	private void init(){
		if(estados == null)
			estados = EstadoEnum.getEstados();
	}

	public InstituicaoService getController() {
		return controller;
	}

	public String getNomeInstituicao() {
		return nomeInstituicao;
	}

	public void setNomeInstituicao(String nomeInstituicao) {
		this.nomeInstituicao = nomeInstituicao;
	}
	
	public List<EstadoEnum> getEstados() {
		return estados;
	}

	public EstadoEnum getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(EstadoEnum estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}

	public void filtrar(){
		this.controller.filtarInstituicoes(nomeInstituicao, estadoSelecionado);
	}

}
