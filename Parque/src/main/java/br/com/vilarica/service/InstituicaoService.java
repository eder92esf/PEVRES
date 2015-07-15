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
import br.com.vilarica.util.FilterUtil;

public class InstituicaoService implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject EntityManager manager;
	private @Inject FilterUtil filterUtil;
	private List<Instituicao> instituicoes;

	public List<Instituicao> getInstituicoes() {
		return instituicoes;
	}

	@Transactional
	public String save(Instituicao instituicao) {
		try {
			if (instituicao.getId() == null) {
				this.manager.persist(instituicao.getContato());
				this.manager.persist(instituicao.getEndereco());

				this.manager.persist(instituicao);
			} else {
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
		/*
		instituicoes = new ArrayList<Instituicao>();

		String sql = "SELECT i FROM Instituicao i JOIN i.municipio m WHERE LOWER(i.nome) like LOWER(:nome) and m.estado = :estado ORDER BY i.nome";

		if (nomeInstituicao == null) {
			nomeInstituicao = "";
		}
		if (estado == null) {
			estado = EstadoEnum.PR;
		}
		
		instituicoes =  manager.createQuery(sql)
				.setParameter("nome", '%'+ nomeInstituicao + '%')
				.setParameter("estado", estado)
				.getResultList();
		*/
		if (estado == null) {
			estado = EstadoEnum.PR;
		}
		instituicoes = filterUtil.filtarInstituicoes(nomeInstituicao, estado);
	}

	@SuppressWarnings("unchecked")
	public List<Municipio> filtarMunicipios(String consulta) {
		/*
		Session s = this.manager.unwrap(Session.class);
		Criteria c = s.createCriteria(Municipio.class);

		if (consulta != null && !consulta.trim().equals("")) {
			c.add(Restrictions.ilike("nome", consulta, MatchMode.ANYWHERE));
		}

		return c.addOrder(Order.asc("nome")).list();
		*/
		return filterUtil.filtarMunicipios(consulta, null);
	}

	public Instituicao porId(Long id) {
		//return this.manager.find(Instituicao.class, id);
		return (Instituicao) filterUtil.porId(Instituicao.class, id);
	}

}
