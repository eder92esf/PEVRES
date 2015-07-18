package br.com.vilarica.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.vilarica.model.EstadoEnum;
import br.com.vilarica.model.ExcursaoEscolar;
import br.com.vilarica.model.ExcursaoTuristica;
import br.com.vilarica.model.Guia;
import br.com.vilarica.model.Instituicao;
import br.com.vilarica.model.Municipio;

public class FilterUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject EntityManager manager;

	private Date dataInicio(Date data) {
		Date inicio = new Date();
		inicio.setYear(data.getYear());
		inicio.setMonth(data.getMonth());
		inicio.setDate(data.getDate());
		inicio.setHours(8);
		inicio.setMinutes(0);
		return inicio;
	}

	private Date dataFim(Date data) {
		Date fim = new Date();
		fim.setYear(data.getYear());
		fim.setMonth(data.getMonth());
		fim.setDate(data.getDate());
		fim.setHours(17);
		fim.setMinutes(0);
		return fim;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<ExcursaoTuristica> listExcursaoTuristica(Date data) {
		List<ExcursaoTuristica> lista = new ArrayList<ExcursaoTuristica>();
		/*
		Session s = manager.unwrap(Session.class);
		Criteria c = s.createCriteria(ExcursaoTuristica.class);

		if (data == null)
			data = new Date();

		Date inicio = dataInicio(data);
		Date fim = dataFim(data);

		lista = c.add(Restrictions.between("dataExcursao", inicio, fim))
				.addOrder(Order.asc("dataExcursao")).list();
		*/
		Date inicio = dataInicio(data);
		Date fim = dataFim(data);

		String sql = "SELECT e FROM ExcursaoTuristica e WHERE e.dataExcursao BETWEEN :dataInicio AND :dataFim";
		lista = this.manager.createQuery(sql)
				.setParameter("dataInicio", inicio)
				.setParameter("dataFim", fim).getResultList();
		return lista;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<ExcursaoEscolar> listExcursaoEscolar(Date data) {
		List<ExcursaoEscolar> lista = new ArrayList<ExcursaoEscolar>();
		/*
		 * Session s = manager.unwrap(Session.class); Criteria c =
		 * s.createCriteria(ExcursaoEscolar.class);
		 * 
		 * if (data == null) data = new Date();
		 * 
		 * Date inicio = dataInicio(data); Date fim = dataFim(data);
		 * 
		 * lista = c.add(Restrictions.between("dataExcursao", inicio, fim))
		 * .addOrder(Order.asc("dataExcursao")).list();
		 */

		Date inicio = dataInicio(data);
		Date fim = dataFim(data);

		String sql = "SELECT e FROM ExcursaoEscolar e WHERE e.dataExcursao BETWEEN :dataInicio AND :dataFim";
		lista = this.manager.createQuery(sql)
				.setParameter("dataInicio", inicio)
				.setParameter("dataFim", fim).getResultList();
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<ExcursaoEscolar> listExcursoesPorGuia(Date data, Long id) {
		if (data == null) {
			data = new Date();
			data.setHours(8);
			data.setMinutes(0);
		}
		List<ExcursaoEscolar> lista = new ArrayList<ExcursaoEscolar>();
		Date inicio = dataInicio(data);
		Date fim = dataFim(data);

		String sql = "SELECT e FROM ExcursaoEscolar e JOIN e.guia WHERE e.guia.id = :id AND e.dataExcursao BETWEEN :dataInicio AND :dataFim";
		lista = this.manager.createQuery(sql).setParameter("id", id)
				.setParameter("dataInicio", inicio)
				.setParameter("dataFim", fim).getResultList();
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Municipio> filtarMunicipios(String consulta, EstadoEnum estado) {
		Session s = this.manager.unwrap(Session.class);
		Criteria c = s.createCriteria(Municipio.class);
		List<Municipio> lista = new ArrayList<Municipio>();

		if (consulta != null && !consulta.trim().equals("")) {
			c.add(Restrictions.ilike("nome", consulta, MatchMode.ANYWHERE));
		}
		if (estado != null) {
			c.add(Restrictions.eq("estado", estado));
		}
		lista = c.addOrder(Order.asc("nome")).list();
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Guia> filtarGuias(String consulta) {
		Session s = this.manager.unwrap(Session.class);
		Criteria c = s.createCriteria(Guia.class);
		List<Guia> lista = new ArrayList<Guia>();

		if (consulta != null && !consulta.trim().equals("")) {
			c.add(Restrictions.ilike("nome", consulta, MatchMode.ANYWHERE));
		}

		lista = c.addOrder(Order.asc("nome")).list();
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Instituicao> filtarInstituicoes(String nomeInstituicao,
			EstadoEnum estado) {
		List<Instituicao> lista = new ArrayList<Instituicao>();
		String sql = "SELECT i FROM Instituicao i JOIN i.municipio m WHERE LOWER(i.nome) like LOWER(:nome) and m.estado = :estado ORDER BY i.nome";

		if (nomeInstituicao == null) {
			nomeInstituicao = "";
		}
		if (estado == null) {
			estado = EstadoEnum.PR;
		}

		lista = manager.createQuery(sql)
				.setParameter("nome", '%' + nomeInstituicao + '%')
				.setParameter("estado", estado).getResultList();
		return lista;
	}

	public List<Instituicao> filtrarInstituicoes(String consulta) {
		List<Instituicao> instituicoes = new ArrayList<Instituicao>();
		Session s = this.manager.unwrap(Session.class);
		Criteria c = s.createCriteria(Instituicao.class);

		if (consulta == null)
			consulta = "";

		instituicoes = c
				.add(Restrictions.ilike("nome", consulta, MatchMode.ANYWHERE))
				.addOrder(Order.asc("nome")).list();
		return instituicoes;
	}

	public Object porId(Class classe, Long id) {
		return this.manager.find(classe, id);
	}
}
