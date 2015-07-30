package br.com.vilarica.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.vilarica.model.Grupo;
import br.com.vilarica.util.FilterUtil;

public class GrupoService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private @Inject EntityManager manager;
	private @Inject FilterUtil filterUtil;
	
	public Grupo porId(Long id){
		//return (Grupo) filterUtil.porId(Grupo.class, id);
		return this.manager.find(Grupo.class, id);
	}

}
