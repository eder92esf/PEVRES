package br.com.vilarica.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.vilarica.model.Grupo;

public class Grupos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	
	public Grupo porId(Long id) {
		return manager.find(Grupo.class, id);
	}
	
	public List<Grupo> listar() {
		return manager.createQuery("from Grupo", Grupo.class).getResultList();
	}
	
	
}
