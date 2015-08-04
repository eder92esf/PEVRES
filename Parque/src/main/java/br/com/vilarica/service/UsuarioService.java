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
	public String updatePassword(Usuario u){
		try {
			this.manager.merge(u);
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}
	
	@Transactional
	public String saveOrUpdate(Usuario usuario){
		try {
			Usuario aux = filterUtil.getUserByEmail(usuario.getEmail()); 
			if(aux != null && !usuario.getId().equals(aux.getId())){
				return "Endereço de e-mail já está sendo utilizado!";
			}
			if(usuario.getId() == null){
				this.manager.persist(usuario);
			}else{
				this.manager.merge(usuario);
				this.manager.flush();
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
			Usuario u = this.manager.getReference(Usuario.class, usuario.getId());
			u.setGrupo(null);
			this.manager.remove(u);
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