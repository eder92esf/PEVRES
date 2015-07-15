package br.com.vilarica.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.vilarica.model.Acompanhante;
import br.com.vilarica.util.FilterUtil;

public class AcompanhanteService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private @Inject EntityManager manager;
	private @Inject FilterUtil filterUtil;
	
	public Acompanhante porId(Long id){
		//return this.manager.find(Acompanhante.class, id);
		return (Acompanhante) filterUtil.porId(Acompanhante.class, id);
	}

}