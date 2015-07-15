package br.com.vilarica.service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.vilarica.jpa.Transactional;
import br.com.vilarica.model.EscolaridadeEnum;
import br.com.vilarica.model.ExcursaoEscolar;
import br.com.vilarica.model.ExcursaoTuristica;
import br.com.vilarica.model.Guia;
import br.com.vilarica.model.Instituicao;
import br.com.vilarica.model.MeioTransporteEnum;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.model.SexoEnum;
import br.com.vilarica.model.StatusExcursao;
import br.com.vilarica.model.TipoAtividadeEnum;
import br.com.vilarica.model.TipoAtividadeExcursao;
import br.com.vilarica.util.FilterUtil;

public class AgendaExcursaoService implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject EntityManager manager;
	private @Inject FilterUtil filterUtil;

	private List<MeioTransporteEnum> meiosTransporte;
	private List<SexoEnum> sexos;
	private List<EscolaridadeEnum> escolaridades;
	private List<StatusExcursao> statusExcursao;
	private String[] atividadesValue;

	private List<Instituicao> instituicoes;
	private List<ExcursaoEscolar> excursoes;
	private boolean desabilitado;
	private boolean imputavel;

	@PostConstruct
	private void init() {
		if (meiosTransporte == null)
			this.meiosTransporte = MeioTransporteEnum.getMeiosTransporte();
		if (sexos == null)
			this.sexos = SexoEnum.getSexos();
		if (escolaridades == null)
			this.escolaridades = EscolaridadeEnum.getEscolaridades();
		if (statusExcursao == null)
			this.statusExcursao = StatusExcursao.getStatusExcursao();
	}

	public String[] getAtividadesValue() {
		return atividadesValue;
	}

	public void setAtividadesValue(String[] atividadesValue) {
		this.atividadesValue = atividadesValue;
	}

	public List<ExcursaoEscolar> getExcursoes() {
		return excursoes;
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

	public List<StatusExcursao> getStatusExcursao() {
		return statusExcursao;
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
	
	public List<Guia> filtrarGuias(String consulta){
		return filterUtil.filtarGuias(consulta);
	}

	public List<Instituicao> filtrarInstituicoes(String consulta) {
		/*
		 * instituicoes = new ArrayList<Instituicao>(); Session s =
		 * this.manager.unwrap(Session.class); Criteria c =
		 * s.createCriteria(Instituicao.class);
		 * 
		 * if (consulta == null) consulta = "";
		 * 
		 * instituicoes = c .add(Restrictions.ilike("nome", consulta,
		 * MatchMode.ANYWHERE)) .addOrder(Order.asc("nome")).list(); return
		 * instituicoes;
		 */
		instituicoes = filterUtil.filtrarInstituicoes(consulta);
		return instituicoes;
	}

	@Transactional
	public String checkinExcursaoTuristica(ExcursaoTuristica excursaoTuristica) {
		try {
			ChecaTipoAtividadeExcursao(excursaoTuristica);
			excursaoTuristica.setDataExcursao(new Date());

			System.out.println(excursaoTuristica);

			if (excursaoTuristica.getId() == null) {
				this.manager.persist(excursaoTuristica.getVisitanteMaster()
						.getContato());
				this.manager.persist(excursaoTuristica.getVisitanteMaster()
						.getEndereco());
				this.manager.persist(excursaoTuristica.getVisitanteMaster());
				this.manager.persist(excursaoTuristica);
			} else {
				this.manager.merge(excursaoTuristica.getVisitanteMaster()
						.getContato());
				this.manager.merge(excursaoTuristica.getVisitanteMaster()
						.getEndereco());
				this.manager.merge(excursaoTuristica.getVisitanteMaster());
				this.manager.merge(excursaoTuristica);
			}
			/* LIMPAR EXCURSAO AO SALVAR NO MANAGE BEAN */
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

	@Transactional
	public String agendaExcursaoEscolar(ExcursaoEscolar excursaoEscolar) {
		try {
			System.out.println(excursaoEscolar);
			if (excursaoEscolar.getId() == null) {
				this.manager.persist(excursaoEscolar);
			} else {
				this.manager.merge(excursaoEscolar);
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	public void listExcursoes(Date data, Long id) {
		/*
		if (data == null) {
			data = new Date();
			data.setHours(8);
			data.setMinutes(0);
		}
		this.excursoes = new ArrayList<ExcursaoEscolar>();
		Date inicio = new Date();
		inicio.setYear(data.getYear());
		inicio.setMonth(data.getMonth());
		inicio.setDate(data.getDate());
		inicio.setHours(8);
		inicio.setMinutes(0);

		Date fim = new Date();
		fim.setYear(data.getYear());
		fim.setMonth(data.getMonth());
		fim.setDate(data.getDate());
		fim.setHours(17);
		fim.setMinutes(0);

		// String sql =
		// "SELECT e FROM ExcursaoEscolar e WHERE e.dataExcursao BETWEEN :dataInicio AND :dataFim";
		String sql = "SELECT e FROM ExcursaoEscolar e JOIN e.guia g ON g.id = :id  WHERE e.dataExcursao BETWEEN :dataInicio AND :dataFim";
		this.excursoes = this.manager.createQuery(sql)
				.setParameter("id", id)
				.setParameter("dataInicio", inicio)
				.setParameter("dataFim", fim).getResultList();
		*/
		this.excursoes = this.filterUtil.listExcursoes(data, id);
	}

	public boolean agendar(Date nova) {
		for (ExcursaoEscolar e : this.excursoes) {
			boolean agendavel = agendavel(e.getDataExcursao(), nova);
			if (!agendavel)
				return false;
		}
		return true;
	}

	private boolean agendavel(Date agendadaInicio, Date novaInicio) {
		Date agendadaFim = new Date();
		agendadaFim.setHours(agendadaInicio.getHours() + 2);
		agendadaFim.setMinutes(agendadaInicio.getMinutes());

		Date novaFim = new Date();
		novaFim.setHours(novaInicio.getHours() + 2);
		novaFim.setMinutes(novaInicio.getMinutes());

		if ((novaInicio.getHours() >= agendadaInicio.getHours())
				&& (novaInicio.getHours() < agendadaFim.getHours())) {
			return false;
		} else if (novaInicio.getHours() == agendadaFim.getHours()) {
			if (novaInicio.getMinutes() < agendadaFim.getMinutes())
				return false;
		} else if (novaFim.getHours() >= agendadaInicio.getHours()
				&& novaFim.getHours() < agendadaFim.getHours()) {
			if (novaFim.getMinutes() != agendadaInicio.getMinutes())
				return false;
		} else if (novaFim.getHours() == agendadaInicio.getHours()
				&& novaFim.getMinutes() > agendadaInicio.getMinutes()) {
			return false;
		}
		return true;
	}
}