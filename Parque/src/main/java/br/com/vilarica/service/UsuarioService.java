package br.com.vilarica.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.vilarica.jpa.Transactional;
import br.com.vilarica.model.Grupo;
import br.com.vilarica.model.Usuario;
import br.com.vilarica.util.FilterUtil;

public class UsuarioService implements Serializable{

	private static final long serialVersionUID = 1L;
	private @Inject EntityManager manager;
	private @Inject FilterUtil filterUtil;
	private List<Usuario> usuarios;
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	@Transactional
	public String saveOrUpdate(Usuario usuario){
		try {
			if(usuario.getId() == null){
				this.manager.persist(usuario);
			}else{
				this.manager.merge(usuario);
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}
	
	@Transactional
	public String remove(Usuario usuario){
		try {
			usuario.setGrupo(null);
			this.manager.remove(usuario);
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}
	
	public List<Grupo> filtrarGrupos(){
		return this.filterUtil.filtrarGrupos();
	}
	
	public void filtrar(String consulta){
		this.usuarios = this.filterUtil.filtrarUsuarios(consulta);
	}
	
	public Usuario porId(Long id){
		return (Usuario) filterUtil.porId(Usuario.class, id);
	}
}