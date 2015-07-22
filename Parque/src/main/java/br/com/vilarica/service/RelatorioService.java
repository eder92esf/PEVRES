package br.com.vilarica.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import br.com.vilarica.report.Relatorio;
import br.com.vilarica.util.FilterUtil;

public class RelatorioService implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private @Inject Relatorio relatorio;
	private @Inject FilterUtil filterUtil;
	
	public void gerarRelatorio(Date inicio, Date fim){
		try {
			System.out.println("\nCHAMOU GERAR RELATORIO IN CONTROLLER");
			relatorio.setEscolars(filterUtil.relatorioExcursaoEscolar(inicio, fim));
			relatorio.setTuristicas(filterUtil.relatorioExcursaoTuristica(inicio, fim));
			relatorio.gerar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
