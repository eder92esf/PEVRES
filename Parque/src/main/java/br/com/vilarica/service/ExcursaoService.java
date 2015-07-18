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
import br.com.vilarica.model.Excursao;
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

public class ExcursaoService implements Serializable {

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
	private List<Guia> guias;
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

	public List<Guia> getGuias() {
		return guias;
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

	public Date getTimeStamp() {
		return Calendar.getInstance().getTime();
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
		return filterUtil.filtarMunicipios(consulta, null);
	}

	public List<Guia> filtrarGuias(String consulta) {
		guias = filterUtil.filtarGuias(consulta);
		return guias;
	}

	public List<Instituicao> filtrarInstituicoes(String consulta) {
		instituicoes = filterUtil.filtrarInstituicoes(consulta);
		return instituicoes;
	}

	public List<ExcursaoTuristica> listExcursaoTuristica(Date data) {
		return filterUtil.listExcursaoTuristica(data);
	}

	public List<ExcursaoEscolar> listExcursaoEscolar(Date data) {
		return filterUtil.listExcursaoEscolar(data);
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
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	private void ChecaTipoAtividadeExcursao(Excursao excursao) {
		if (atividadesValue != null) {
			for (int i = 0; i < atividadesValue.length; i++) {
				// Convertendo para Tipo de Ativade para Excursao
				String atividade = atividadesValue[i];
				TipoAtividadeExcursao t = new TipoAtividadeExcursao();
				t.setAtividadeEnum(TipoAtividadeEnum.getEnum(atividade));

				// Verificando se a lista ja possui a atividade
				boolean existe = false;
				for (TipoAtividadeExcursao aux : excursao.getAtividades()) {
					if (aux.getAtividadeEnum().equals(t.getAtividadeEnum())) {
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
			ChecaTipoAtividadeExcursao(excursaoEscolar);
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

	public void listExcursoesEscolar(Date data) {
		this.excursoes = this.filterUtil.listExcursaoEscolar(data);
	}

	public void listExcursoesPorGuia(Date data, Long id) {
		this.excursoes = filterUtil.listExcursoesPorGuia(data, id);
	}

	public boolean agendar(Date nova) {
		for (ExcursaoEscolar e : this.excursoes) {
			boolean agendavel = agendavel(e.getDataExcursao(), nova);
			if (!agendavel)
				return false;
		}
		return true;
	}

	/* CHECANDO SE EH POSSIVEL AGENDAR EXCURSAO APOS ATUALIZAR DATA E HORARIO */
	public boolean agendar(Date nova, Long id) {
		ExcursaoEscolar ee = excursaoEscolarPorId(id);
		if (!ee.getDataExcursao().equals(nova)) {
			// Procurando conflitos de horario entre de excursoes
			for (ExcursaoEscolar e : this.excursoes) {
				// Não a necessidade comparar o data excursao atualizada com ela mesma
				if(!e.getId().equals(id)){
					boolean agendavel = agendavel(e.getDataExcursao(), nova);
					if (!agendavel) {
						return false;
					}
				}
			}
			return true;
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

	public ExcursaoEscolar excursaoEscolarPorId(Long id) {
		return (ExcursaoEscolar) filterUtil.porId(ExcursaoEscolar.class, id);
	}

	public ExcursaoTuristica excursaoTuristicaPorId(Long id) {
		return (ExcursaoTuristica) filterUtil
				.porId(ExcursaoTuristica.class, id);
	}
}