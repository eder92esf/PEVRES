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
	private Date horario;

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

	@SuppressWarnings("deprecation")
	public Date getDataImputavel() {
		Date dataImputavel = Calendar.getInstance().getTime();
		dataImputavel.setYear(dataImputavel.getYear() - 18);
		return dataImputavel;
	}

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
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

	@SuppressWarnings("deprecation")
	public String checkinExcursao(Excursao excursao) {
		try {
			ChecaTipoAtividadeExcursao(excursao);
			excursao.getDataExcursao().setHours(horario.getHours());
			excursao.getDataExcursao().setMinutes(horario.getMinutes());
			
			System.out.println(excursao);

			/*
			 * if (excursao.getId() == null) { this.manager.persist(excursao); }
			 * else { this.manager.merge(excursao); }
			 */
			excursao = new Excursao();
			/* LIMPAR EXCURSAO AO SALVAR NO MANAGE BEAN */
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
	
	public boolean isImputavel(Date dataNascimento){
		Date atual = Calendar.getInstance().getTime();
		int ano = atual.getYear() - dataNascimento.getYear();
		
		if(ano > 17)
			return true;
		return false;
	}
}
