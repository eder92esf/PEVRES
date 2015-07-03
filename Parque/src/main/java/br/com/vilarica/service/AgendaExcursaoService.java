package br.com.vilarica.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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

import br.com.vilarica.jpa.Transactional;
import br.com.vilarica.model.EscolaridadeEnum;
import br.com.vilarica.model.ExcursaoEscolar;
import br.com.vilarica.model.ExcursaoTuristica;
import br.com.vilarica.model.Instituicao;
import br.com.vilarica.model.MeioTransporteEnum;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.model.SexoEnum;
import br.com.vilarica.model.TipoAtividadeEnum;
import br.com.vilarica.model.TipoAtividadeExcursao;

public class AgendaExcursaoService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private @Inject EntityManager manager;
	
	private List<MeioTransporteEnum> meiosTransporte;
	private List<SexoEnum> sexos;
	private List<EscolaridadeEnum> escolaridades;
	
	private String[] atividadesValue;
	private List<Instituicao> instituicoes;
	private boolean desabilitado;
	private boolean imputavel;
	
	@PostConstruct
	private void init() {
		if (meiosTransporte == null)
			this.meiosTransporte = MeioTransporteEnum.getMeiosTransporte();
		if(sexos == null)
			this.sexos = SexoEnum.getSexos();
		if(escolaridades == null)
			this.escolaridades = EscolaridadeEnum.getEscolaridades();
	}

	public String[] getAtividadesValue() {
		return atividadesValue;
	}

	public void setAtividadesValue(String[] atividadesValue) {
		this.atividadesValue = atividadesValue;
	}

	public boolean isDesabilitado() {
		return desabilitado;
	}

	public void setDesabilitado(boolean desabilitado) {
		this.desabilitado = desabilitado;
	}

	public boolean isImputavel() {
		return imputavel;
	}

	public void setImputavel(boolean imputavel) {
		this.imputavel = imputavel;
	}

	public List<MeioTransporteEnum> getMeiosTransporte() {
		return meiosTransporte;
	}

	public List<SexoEnum> getSexos() {
		return sexos;
	}

	public List<EscolaridadeEnum> getEscolaridades() {
		return escolaridades;
	}

	public List<Instituicao> getInstituicoes() {
		return instituicoes;
	}
	
	public Date getDataAtual() {
		Date aux = new Date();
		aux.setHours(8);
		aux.setMinutes(15);
		return aux;
	}
	
	@SuppressWarnings("deprecation")
	public void checaImputavel(Date dataNascimento) {
		Date atual = Calendar.getInstance().getTime();
		int ano = atual.getYear() - dataNascimento.getYear();

		if (ano > 18)
			this.imputavel = true;
		else if (ano == 18) {
			if (dataNascimento.getMonth() < atual.getMonth())
				this.imputavel = true;
			else if (dataNascimento.getMonth() == atual.getMonth()) {
				if (atual.getDay() >= dataNascimento.getDay())
					this.imputavel = true;
				else
					this.imputavel = false;
			} else
				this.imputavel = false;
		} else {
			this.imputavel = false;
		}

		// Invertendo o Valor para Hab/Des o campo trilha
		this.imputavel = !this.imputavel;
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
	
	@SuppressWarnings("unchecked")
	public List<Municipio> filtarMunicipios(String consulta) {
		Session s = this.manager.unwrap(Session.class);
		Criteria c = s.createCriteria(Municipio.class);

		if (consulta != null && !consulta.trim().equals("")) {
			c.add(Restrictions.ilike("nome", consulta, MatchMode.ANYWHERE));
		}

		return c.addOrder(Order.asc("nome")).list();
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
	
	@Transactional
	public String checkinExcursaoTuristica(ExcursaoTuristica excursaoTuristica) {
		try {
			ChecaTipoAtividadeExcursao(excursaoTuristica);
			excursaoTuristica.setDataExcursao(new Date());

			System.out.println(excursaoTuristica);

			if (excursaoTuristica.getId() == null) {
				this.manager
						.persist(excursaoTuristica.getVisitanteMaster().getContato());
				this.manager.persist(excursaoTuristica.getVisitanteMaster()
						.getEndereco());
				this.manager.persist(excursaoTuristica.getVisitanteMaster());
				this.manager.persist(excursaoTuristica);
			} else {
				this.manager.merge(excursaoTuristica.getVisitanteMaster().getContato());
				this.manager.merge(excursaoTuristica.getVisitanteMaster().getEndereco());
				this.manager.merge(excursaoTuristica.getVisitanteMaster());
				this.manager.merge(excursaoTuristica);
			}
			/* LIMPAR EXCURSAO AO SALVAR NO MANAGE BEAN */
			excursaoTuristica = new ExcursaoTuristica();
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	private void ChecaTipoAtividadeExcursao(ExcursaoTuristica excursao) {
		if (atividadesValue != null) {
			for (int i = 0; i < atividadesValue.length; i++) {
				// Convertendo para Tipo de Ativade para Excursao
				String atividade = atividadesValue[i];
				TipoAtividadeExcursao t = new TipoAtividadeExcursao();
				t.setAtividadeEnum(TipoAtividadeEnum.getEnum(atividade));

				// Verificando se a lista ja possui a atividade
				boolean existe = false;
				for (TipoAtividadeExcursao aux : excursao.getAtividades()) {
					if (aux.equals(t)) {
						existe = true;
						break;
					}
				}
				if (!existe)
					excursao.getAtividades().add(t);
			}
		}
	}
	
	public String agendaExcursaoEscolar(ExcursaoEscolar excursaoEscolar) {
		try {
			System.out.println(excursaoEscolar);
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}
}
