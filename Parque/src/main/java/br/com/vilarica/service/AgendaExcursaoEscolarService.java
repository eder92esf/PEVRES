package br.com.vilarica.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.vilarica.model.EscolaridadeEnum;
import br.com.vilarica.model.ExcursaoEscolar;
import br.com.vilarica.model.Instituicao;
import br.com.vilarica.model.MeioTransporteEnum;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.model.SexoEnum;

public class AgendaExcursaoEscolarService implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject EntityManager manager;

	private Date horario;
	private List<MeioTransporteEnum> meiosTransporte;
	private List<SexoEnum> sexos;
	private List<EscolaridadeEnum> escolaridades;
	private String[] atividadesValue;
	private List<Instituicao> instituicoes;
	private boolean desabilitado;

	@PostConstruct
	private void init() {
		if (meiosTransporte == null)
			this.meiosTransporte = MeioTransporteEnum.getMeiosTransporte();
		if(sexos == null)
			this.sexos = SexoEnum.getSexos();
		if(getEscolaridades() == null)
			this.escolaridades = EscolaridadeEnum.getEscolaridades();
	}


	public Date getDataAtual() {
		Date aux = new Date();
		aux.setHours(8);
		aux.setMinutes(15);
		return aux;
	}

	public List<MeioTransporteEnum> getMeiosTransporte() {
		return meiosTransporte;
	}

	public List<Instituicao> getInstituicoes() {
		return instituicoes;
	}

	public boolean isDesabilitado() {
		return desabilitado;
	}

	public String[] getAtividadesValue() {
		return atividadesValue;
	}

	public void setAtividadesValue(String[] atividadesValue) {
		this.atividadesValue = atividadesValue;
	}

	public List<SexoEnum> getSexos() {
		return sexos;
	}

	public List<EscolaridadeEnum> getEscolaridades() {
		return escolaridades;
	}

	public List<Instituicao> filtrarInstituicoes(String consulta) {
		instituicoes = new ArrayList<Instituicao>();
		Session s = this.manager.unwrap(Session.class);
		Criteria c = s.createCriteria(Instituicao.class);

		if (consulta == null)
			consulta = "";

		instituicoes = c
				.add(Restrictions.ilike("nome", consulta, MatchMode.ANYWHERE))
				.addOrder(Order.asc("nome")).list();
		return instituicoes;
	}

	public String agendaExcursao(ExcursaoEscolar excursaoEscolar) {
		try {
			excursaoEscolar.getDataExcursao().setHours(horario.getHours());
			excursaoEscolar.getDataExcursao().setMinutes(horario.getMinutes());
			System.out.println(excursaoEscolar);
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return e.getMessage();
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

	public void desabilitaAtividade(Date data) {
		int horario = data.getHours();
		int minutos = data.getMinutes();
		System.out.println(data);
		
		if (horario == 15 && minutos > 30) {
			this.desabilitado = true;
		} else {
			this.desabilitado = false;
		}
	}
}
