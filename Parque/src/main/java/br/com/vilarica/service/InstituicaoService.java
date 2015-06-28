package br.com.vilarica.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.vilarica.jpa.Transactional;
import br.com.vilarica.model.EstadoEnum;
import br.com.vilarica.model.Instituicao;
import br.com.vilarica.model.Municipio;

public class InstituicaoService implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject EntityManager manager;
	private List<Instituicao> instituicoes;

	public List<Instituicao> getInstituicoes() {
		return instituicoes;
	}

	@Transactional
	public String save(Instituicao instituicao, Municipio oldMunicipio) {
		try {
			List<Instituicao> lista = instituicao.getMunicipio()
					.getInstituicoes();
			if (instituicao.getId() == null) {
				this.manager.persist(instituicao.getContato());
				this.manager.persist(instituicao.getEndereco());

				this.manager.persist(instituicao);

				lista.add(instituicao);
				instituicao.getMunicipio().setInstituicoes(lista);
				this.manager.merge(instituicao.getMunicipio());
			} else {

				if (oldMunicipio != null) {
					for (Instituicao i : oldMunicipio.getInstituicoes()) {
						if (i.getId().equals(instituicao.getId())) {
							oldMunicipio.getInstituicoes().remove(instituicao);
							this.manager.merge(oldMunicipio);
							break;
						}
					}
					lista.add(instituicao);
					instituicao.getMunicipio().setInstituicoes(lista);
					this.manager.merge(instituicao.getMunicipio());
				}
				this.manager.merge(instituicao);
			}
			instituicao = new Instituicao();
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	@SuppressWarnings("unchecked")
	public void filtarInstituicoes(String nomeInstituicao, EstadoEnum estado) {
		instituicoes = new ArrayList<Instituicao>();

		String sql = "SELECT i FROM Instituicao i JOIN i.municipio m WHERE LOWER(i.nome) like LOWER(:nome) and m.estado = :estado ORDER BY i.nome";

		if (nomeInstituicao == null) {
			nomeInstituicao = "";
		}
		if (estado == null) {
			estado = EstadoEnum.PR;
		}
		
		System.out.println("\nNOME: " + nomeInstituicao + " ESTADO: " + estado.getUf());

		instituicoes =  manager.createQuery(sql)
				.setParameter("nome", '%'+ nomeInstituicao + '%')
				.setParameter("estado", estado)
				.getResultList();
		
		System.out.println("\nTAMANHO DA LISTA " + instituicoes.size());
		
		for (Instituicao instituicao : instituicoes) {
			System.out.println("\n" + instituicao);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Municipio> filtarMunicipios(String consulta) {
		Session s = this.manager.unwrap(Session.class);
		Criteria c = s.createCriteria(Municipio.class);

		if (consulta != null && !consulta.trim().equals("")) {
			c.add(Restrictions.ilike("nome", consulta, MatchMode.ANYWHERE));
		}

		return c.addOrder(Order.asc("nome")).list();
	}

	public Instituicao porId(Long id) {
		return this.manager.find(Instituicao.class, id);
	}

}
