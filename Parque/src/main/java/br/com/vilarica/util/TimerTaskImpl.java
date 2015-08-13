package br.com.vilarica.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.persistence.EntityManager;

import br.com.vilarica.jpa.JpaUtil;
import br.com.vilarica.model.ExcursaoEscolar;
import br.com.vilarica.model.StatusExcursao;

public class TimerTaskImpl extends TimerTask {

	private EntityManager manager;

	public TimerTaskImpl() {
		this.manager = JpaUtil.getEntityManager();
	}

	@Override
	public void run() {
		String sql = "SELECT e FROM ExcursaoEscolar e WHERE e.dataExcursao BETWEEN :dataInicio AND :dataFim";

		Date inicio = Calendar.getInstance().getTime();
		inicio.setHours(0);
		inicio.setMinutes(0);

		Date fim = Calendar.getInstance().getTime();
		fim.setHours(23);
		fim.setMinutes(59);

		List<ExcursaoEscolar> lista = this.manager.createQuery(sql).setParameter("dataInicio", inicio)
				.setParameter("dataFim", fim).getResultList();
		for (ExcursaoEscolar excursaoEscolar : lista) {
			//ExcursaoEscolar e = this.manager.getReference(ExcursaoEscolar.class, excursaoEscolar.getId());
			//e.setStatus(StatusExcursao.CANCELADA);
			excursaoEscolar.setStatus(StatusExcursao.CANCELADA);
			this.manager.getTransaction().begin();
			this.manager.merge(excursaoEscolar);
			this.manager.getTransaction().commit();
		}
	}

}
