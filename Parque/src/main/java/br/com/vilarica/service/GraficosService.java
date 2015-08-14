package br.com.vilarica.service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.vilarica.model.Excursao;
import br.com.vilarica.model.ExcursaoEscolar;
import br.com.vilarica.model.ExcursaoTuristica;
import br.com.vilarica.model.StatusExcursao;

public class GraficosService implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private @Inject EntityManager manager;
	private Map<Integer, Integer> porVisitantes = new HashMap<>();
	private int numeroDias;

	public Map<Integer, Integer> getPorVisitantes() {
		return porVisitantes;
	}
	
	public void gerarGraficos() {
		setNumeroDias(getNumeroDias());

		Calendar dataInicial = Calendar.getInstance();
		
		dataInicial.set(Calendar.HOUR_OF_DAY, 0);
		dataInicial.set(Calendar.MINUTE, 0);
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, getNumeroDias() * -1);
		
		Session s = this.manager.unwrap(Session.class);
		Criteria c = s.createCriteria(ExcursaoEscolar.class);

		List<ExcursaoEscolar> escolars = null;
		List<ExcursaoTuristica> turisticas = null;
		
		escolars = c.add(Restrictions.ge("dataExcursao", dataInicial.getTime()))
					.add(Restrictions.eq("status", StatusExcursao.REALIZADA))
					.list();

		c = s.createCriteria(ExcursaoTuristica.class);
		turisticas = c.add(Restrictions.ge("dataExcursao", dataInicial.getTime()))
					  .list();
		
		for (ExcursaoTuristica t: turisticas) {
			create(t);
		}
		for (ExcursaoEscolar e : escolars) {
			create(e);
		}
	}
	
	private void create(Excursao e){
		int chave = e.getDataExcursao().getMonth() + 1;
		int visitantes = e.getAcompanhantes().size();
		if(e instanceof ExcursaoTuristica)
			visitantes++;
		
		if(porVisitantes.containsKey(chave)){
			int oldValue = porVisitantes.get(chave);
			visitantes += oldValue;
		}
		porVisitantes.put(chave, visitantes);
		
	}

	public int getNumeroDias() {
		return numeroDias;
	}

	public void setNumeroDias(int numeroDias) {
		this.numeroDias = numeroDias;
	}
}
