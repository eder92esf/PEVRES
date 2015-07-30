package br.com.vilarica.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import br.com.vilarica.jpa.Transactional;
import br.com.vilarica.model.Usuario;
import br.com.vilarica.service.NegocioException;

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	@Transactional //Opcional, pois quem chama esse método (CadastroExemploService) já tem a anotação.
	public Usuario guardar(Usuario usuario) {
		return manager.merge(usuario);
	}
	
	@Transactional //Nesse caso como não tem regra de negócio, esse método é chamado diretamente, não passar por uma
	//classe de serviço
	public void remover(Usuario usuario){
		try{
			usuario = porId(usuario.getId());
			manager.remove(usuario);
			manager.flush();
		} catch(PersistenceException e){
			throw new NegocioException("Usuario não pode ser excluído! " + e);
		}
	}
	
	public Usuario porId(Long id) {
		return this.manager.find(Usuario.class, id);
	}
	
	public List<Usuario> vendedores() {
		// TODO filtrar apenas vendedores (por um grupo específico)
		return this.manager.createQuery("from Usuario", Usuario.class)
				.getResultList();
	}

	public Usuario porEmail(String email) {
		Usuario usuario = null;
		
		try {
			usuario = this.manager.createQuery("from Usuario where lower(email) = :email", Usuario.class)
				.setParameter("email", email.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			// nenhum usuário encontrado com o e-mail informado
		}
		
		return usuario;
	}
	
}
