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
import br.com.vilarica.model.Grupo;
import br.com.vilarica.model.Guia;
import br.com.vilarica.model.Instituicao;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.model.Usuario;

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

	public List<ExcursaoEscolar> relatorioExcursaoEscolar(Date inicio, Date fim) {
		List<ExcursaoEscolar> lista = new ArrayList<>();

		inicio = dataInicio(inicio);
		fim = dataFim(fim);

		String sql = "SELECT e FROM ExcursaoEscolar e WHERE e.dataExcursao BETWEEN :dataInicio AND :dataFim";
		lista = this.manager.createQuery(sql)
				.setParameter("dataInicio", inicio)
				.setParameter("dataFim", fim).getResultList();

		return lista;
	}

	public List<ExcursaoTuristica> relatorioExcursaoTuristica(Date inicio,
			Date fim) {
		List<ExcursaoTuristica> lista = new ArrayList<>();

		inicio = dataInicio(inicio);
		fim = dataFim(fim);

		String sql = "SELECT e FROM ExcursaoTuristica e WHERE e.dataExcursao BETWEEN :dataInicio AND :dataFim";
		lista = this.manager.createQuery(sql)
				.setParameter("dataInicio", inicio)
				.setParameter("dataFim", fim).getResultList();

		return lista;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<ExcursaoTuristica> listExcursaoTuristica(Date data) {
		List<ExcursaoTuristica> lista = new ArrayList<ExcursaoTuristica>();
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

	public List<Usuario> filtrarUsuarios(String nome) {
		List<Usuario> lista = new ArrayList<Usuario>();

		Session s = this.manager.unwrap(Session.class);
		Criteria c = s.createCriteria(Usuario.class);

		if (nome == null)
			nome = "";

		lista = c.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE))
				.addOrder(Order.asc("nome")).list();
		return lista;
	}
	
	/*
	public Usuario getUser(String nome) {
		Usuario u = null;
		Session s = this.manager.unwrap(Session.class);
		Criteria c = s.createCriteria(Usuario.class);

		if (nome == null)
			nome = "";
		
		u = (Usuario) c.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE)).uniqueResult();
		return u;
	}
	*/
	
	public Usuario getUserByEmail(String email){
		Usuario u = null;
		Session s = this.manager.unwrap(Session.class);
		Criteria c = s.createCriteria(Usuario.class);
		
		u = (Usuario) c.add(Restrictions.eq("email", email)).uniqueResult();
		
		return u;
	}
	
	@SuppressWarnings("unchecked")
	public List<Grupo> filtrarGrupos(){
		List<Grupo> lista = new ArrayList<>();
		Session s = this.manager.unwrap(Session.class);
		Criteria c = s.createCriteria(Grupo.class);
		
		lista = c.addOrder(Order.asc("nome")).list();
		return lista;
	}
	
	/*
	public boolean usersExits(){
		return getUser("") != null ;
	}
	*/

	@SuppressWarnings("unchecked")
	public Object porId(Class classe, Long id) {
		return this.manager.find(classe, id);
	}
}
