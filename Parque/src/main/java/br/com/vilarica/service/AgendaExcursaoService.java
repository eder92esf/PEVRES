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
import br.com.vilarica.model.Excursao;
import br.com.vilarica.model.MeioTransporteEnum;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.model.SexoEnum;
import br.com.vilarica.model.TipoAtividadeEnum;
import br.com.vilarica.model.TipoAtividadeExcursao;

public class AgendaExcursaoService implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject EntityManager manager;
	private List<MeioTransporteEnum> meiosTransporte;
	private List<SexoEnum> sexos;
	private List<EscolaridadeEnum> escolaridades;
	private List<String> tipoAtividades;
	private String[] atividadesValue;
	private boolean imputavel;

	@PostConstruct
	private void init() {
		if (meiosTransporte == null)
			meiosTransporte = MeioTransporteEnum.getMeiosTransporte();
		if (sexos == null)
			sexos = SexoEnum.getSexos();
		if (escolaridades == null)
			escolaridades = EscolaridadeEnum.getEscolaridades();
		if (tipoAtividades == null) {
			tipoAtividades = new ArrayList<String>(3);
			tipoAtividades.add("Vídeo");
			tipoAtividades.add("Museu");
			tipoAtividades.add("Trilha do Lago");
		}
	}

	public Date getDataAtual() {
		return Calendar.getInstance().getTime();
	}

	public List<EscolaridadeEnum> getEscolaridades() {
		return escolaridades;
	}

	public List<SexoEnum> getSexos() {
		return sexos;
	}

	public List<MeioTransporteEnum> getMeiosTransporte() {
		return meiosTransporte;
	}

	public List<String> getTipoAtividades() {
		return tipoAtividades;
	}

	public String[] getAtividadesValue() {
		return atividadesValue;
	}

	public void setAtividadesValue(String[] atividadesValue) {
		this.atividadesValue = atividadesValue;
	}

	public boolean isImputavel() {
		return imputavel;
	}

	public void setImputavel(boolean imputavel) {
		this.imputavel = imputavel;
	}
	
	@Transactional
	public String checkinExcursao(Excursao excursao) {
		try {
			ChecaTipoAtividadeExcursao(excursao);
			excursao.setDataExcursao(new Date());

			System.out.println(excursao);

			if (excursao.getId() == null) {
				this.manager
						.persist(excursao.getVisitanteMaster().getContato());
				this.manager.persist(excursao.getVisitanteMaster()
						.getEndereco());
				this.manager.persist(excursao.getVisitanteMaster());
				this.manager.persist(excursao);
			} else {
				this.manager.merge(excursao.getVisitanteMaster().getContato());
				this.manager.merge(excursao.getVisitanteMaster().getEndereco());
				this.manager.merge(excursao.getVisitanteMaster());
				this.manager.merge(excursao);
			}
			/* LIMPAR EXCURSAO AO SALVAR NO MANAGE BEAN */
			excursao = new Excursao();
			return "";
		} catch (Exception e) {
			e.printStackTrace();
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
}
