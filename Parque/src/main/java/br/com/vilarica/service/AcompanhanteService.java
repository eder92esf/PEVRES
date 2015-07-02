package br.com.vilarica.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.vilarica.model.Acompanhante;

public class AcompanhanteService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private @Inject EntityManager manager;
	
	public Acompanhante porId(Long id){
		return this.manager.find(Acompanhante.class, id);
	}

}
