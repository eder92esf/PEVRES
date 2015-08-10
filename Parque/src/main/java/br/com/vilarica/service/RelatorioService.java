package br.com.vilarica.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import br.com.vilarica.model.Acompanhante;
import br.com.vilarica.model.EscolaridadeEnum;
import br.com.vilarica.model.Excursao;
import br.com.vilarica.model.ExcursaoEscolar;
import br.com.vilarica.model.ExcursaoTuristica;
import br.com.vilarica.model.MeioTransporteEnum;
import br.com.vilarica.model.SexoEnum;
import br.com.vilarica.model.TipoAtividadeExcursao;
import br.com.vilarica.util.FilterUtil;

public class RelatorioService implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject FilterUtil filterUtil;
	private File relatorio;
	private FileWriter fw;
	private BufferedWriter bw;
	private StringBuilder sb = new StringBuilder();

	private List<ExcursaoEscolar> escolars;
	private List<ExcursaoTuristica> turisticas;

	private int totalVisitante;

	// IDADE
	private int visitante0_5anos;
	private int visitante6_12anos;
	private int visitante13_20anos;
	private int visitante21_30anos;
	private int visitante31_50anos;
	private int visitante51_100anos;

	// ESCOLARIDADE
	private int sem_escolaridade;
	private int educacao_infantil;
	private int ensino_fundamental;
	private int ensino_medio;
	private int ensino_superior;

	// MEIO TRANSPORTE
	private int onibus_escolar;
	private int onibus_turismo;
	private int carro;
	private int motocicleta;
	private int bicicleta;
	private int a_pe;
	private int outro;

	// IDADE RELATORIO FINAL
	private int visitante0_5total;
	private int visitante6_12total;
	private int visitante13_20total;
	private int visitante21_30total;
	private int visitante31_50total;
	private int visitante51_100total;

	// ESCOLARIDADE RELATORIO FINAL
	private int sem_escolaridadeTotal;
	private int educacao_infantilTotal;
	private int ensino_fundamentalTotal;
	private int ensino_medioTotal;
	private int ensino_superiorTotal;

	// MEIO TRANSPORTE RELATORIO FINAL
	private int onibus_escolarTotal;
	private int onibus_turismoTotal;
	private int carroTotal;
	private int motocicletaTotal;
	private int bicicletaTotal;
	private int a_peTotal;
	private int outroTotal;

	// ATIVIDADES
	private int video;
	private int museu;
	private int trilha_lago;

	// QUANTIDADE POR SEXO
	private int homens;
	private int mulheres;

	// QUANTIDADE POR MUNICIPIO
	private HashMap<String, Integer> municipios = new HashMap<>();

	public List<ExcursaoEscolar> getEscolars() {
		return escolars;
	}

	public void setEscolars(List<ExcursaoEscolar> escolars) {
		this.escolars = escolars;
	}

	public List<ExcursaoTuristica> getTuristicas() {
		return turisticas;
	}

	public void setTuristicas(List<ExcursaoTuristica> turisticas) {
		this.turisticas = turisticas;
	}

	public File gerar(Date inicio, Date fim) throws Exception {
		if (sb.toString().equals("")) {
			sb.append("Data Excursao;Visitantes;;Idade;;;;;;Escolaridade;;;;;Atividades;Transporte;;;;;;\n")
					.append(";Homens;Mulheres;0-5 anos;6-12 anos;13-20 anos;21-30 anos;31-50 anos;acima de 50 anos;")
					.append("Sem Escolaridade;Educação Infantil;Ensino Fundamental;Ensino Médio;Ensino Superior;")
					.append(";Ônibus Escolar;Ônibus Turismo;Carro;Motocicleta;Bicicleta;A Pé;Outro").append("\n");
		}

		File diretorio = null;
		if (File.separator.equals("\\"))
			diretorio = new File("C:\\PEVRES\\");
		else
			diretorio = new File("/home/PEVRES/");

		if (!diretorio.exists())
			diretorio.mkdirs();

		SimpleDateFormat s = new SimpleDateFormat("dd-MMMM-yyyy");
		String dataCriacao = s.format(new Date());
		relatorio = new File(diretorio.getAbsolutePath() + File.separator + "relatorio-" + dataCriacao + ".csv");

		if (!relatorio.exists())
			relatorio.createNewFile();

		this.escolars = filterUtil.relatorioExcursaoEscolar(inicio, fim);
		this.turisticas = filterUtil.relatorioExcursaoTuristica(inicio, fim);

		criarRelatorioEscolar(this.escolars);
		criarRelatorioTuristico(this.turisticas);
		writeRodape();
		writeByAtividade();
		writeByMunicipio();

		fw = new FileWriter(relatorio);
		bw = new BufferedWriter(fw);
		bw.write(sb.toString());
		bw.flush();

		fw = null;
		bw = null;
		cleanVariaveisTotal();
		System.gc();
		return relatorio;
	}

	private void criarRelatorioEscolar(List<ExcursaoEscolar> lista) throws IOException {
		for (Excursao e : lista) {
			//Excursao e = (Excursao) o;
			preparaRelatorio(e);
		}
	}

	private void criarRelatorioTuristico(List<ExcursaoTuristica> lista) throws IOException {
		for (ExcursaoTuristica e : lista) {
			//ExcursaoTuristica e = (ExcursaoTuristica) o;
			totalVisitante++;
			porIdade(checaIdade(e.getVisitanteMaster().getDataNascimento()));
			porEscolaridade(e.getVisitanteMaster().getEscolaridade());
			porSexo(e.getVisitanteMaster().getSexo());
			porMunicipio(e.getVisitanteMaster().getMunicipio().getNome());
			preparaRelatorio(e);
		}
	}

	private void preparaRelatorio(Excursao e) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		totalVisitante += e.getAcompanhantes().size();
		porMeioTransporte(e);
		porAtividade(e);

		for (Acompanhante a : e.getAcompanhantes()) {
			int aux = checaIdade(a.getDataNascimento());
			porIdade(aux);
			porEscolaridade(a.getEscolaridade());
			porSexo(a.getSexo());
			porMunicipio(a.getMunicipio().getNome());
		}
		writeRelatorio(format, e);
		clean();
	}

	private void writeByAtividade() {
		sb.append("\nAtividades Realizadas por Visitantes\n").append("Vídeo;").append(video).append("\n")
				.append("Museu;").append(museu).append("\n").append("Trilha do Lago;").append(trilha_lago).append("\n");
	}

	private void writeByMunicipio() {
		sb.append("\nVisitantes por Município\n");
		Set s = municipios.keySet();

		for (Iterator iterator = s.iterator(); iterator.hasNext();) {
			String aux = (String) iterator.next();
			sb.append(aux).append(";").append(municipios.get(aux)).append("\n");
		}

		/*
		 * sb.append("\n"); for (Iterator iterator = s.iterator();
		 * iterator.hasNext();) { String aux = (String) iterator.next();
		 * sb.append(municipios.get(aux)).append(";");//.append(municipios.get(
		 * aux)).append("\n"); }
		 */
	}

	private void writeRelatorio(SimpleDateFormat format, Excursao e) {
		sb.append(format.format(e.getDataExcursao())).append(";").append(homens).append(";").append(mulheres)
				.append(";").append(visitante0_5anos).append(";").append(visitante6_12anos).append(";")
				.append(visitante13_20anos).append(";").append(visitante21_30anos).append(";")
				.append(visitante31_50anos).append(";").append(visitante51_100anos).append(";").append(sem_escolaridade)
				.append(";").append(educacao_infantil).append(";").append(ensino_fundamental).append(";")
				.append(ensino_medio).append(";").append(ensino_superior).append(";")
				.append(porTipoAtividade(e.getAtividades())).append(";").append(onibus_escolar).append(";")
				.append(onibus_turismo).append(";").append(carro).append(";").append(motocicleta).append(";")
				.append(bicicleta).append(";").append(a_pe).append(";").append(outro).append("\n");
	}

	private void writeRodape() {
		sb.append("\nTotal;").append(totalVisitante).append(";;").append(visitante0_5total).append(";")
				.append(visitante6_12total).append(";").append(visitante13_20total).append(";")
				.append(visitante21_30total).append(";").append(visitante31_50total).append(";")
				.append(visitante51_100total).append(";").append(sem_escolaridadeTotal).append(";")
				.append(educacao_infantilTotal).append(";").append(ensino_fundamentalTotal).append(";")
				.append(ensino_medioTotal).append(";").append(ensino_superiorTotal).append(";").append(";")
				.append(onibus_escolarTotal).append(";").append(onibus_turismoTotal).append(";").append(carroTotal)
				.append(";").append(motocicletaTotal).append(";").append(bicicletaTotal).append(";").append(a_peTotal)
				.append(";").append(outroTotal).append("\n");
	}

	private String porTipoAtividade(List<TipoAtividadeExcursao> atividades) {
		StringBuilder aux = new StringBuilder();
		for (int i = 0; i < atividades.size(); i++) {
			String atividade = atividades.get(i).getAtividadeEnum().getAtividade();
			aux.append(atividade);
			if (i < atividades.size() - 1)
				aux.append(" - ");
		}
		return aux.toString();
	}

	private void porAtividade(Excursao e) {
		for (TipoAtividadeExcursao atividade : e.getAtividades()) {
			if (atividade.getAtividadeEnum().getAtividade().equals("Vídeo")) {
				video += e.getAcompanhantes().size();
				if (e instanceof ExcursaoTuristica) {
					video++;
				}
			} else if (atividade.getAtividadeEnum().getAtividade().equals("Museu")) {
				museu += e.getAcompanhantes().size();
				if (e instanceof ExcursaoTuristica) {
					museu++;
				}
			} else if (atividade.getAtividadeEnum().getAtividade().equals("Trilha do Lago")) {
				trilha_lago += e.getAcompanhantes().size();
				trilha_lago++;
			}
		}
	}

	private void porMeioTransporte(Excursao e) {
		if (e.getMeioTransporte().equals(MeioTransporteEnum.ONIBUS_ESCOLAR)) {
			onibus_escolar = e.getAcompanhantes().size();
			onibus_escolarTotal += e.getAcompanhantes().size();
			if (e instanceof ExcursaoTuristica) {
				onibus_escolar++;
				onibus_escolarTotal++;
			}
		} else if (e.getMeioTransporte().equals(MeioTransporteEnum.ONIBUS_TURISMO)) {
			onibus_turismo = e.getAcompanhantes().size();
			onibus_turismoTotal += e.getAcompanhantes().size();
			if (e instanceof ExcursaoTuristica) {
				onibus_turismo++;
				onibus_escolarTotal++;
			}
		} else if (e.getMeioTransporte().equals(MeioTransporteEnum.CARRO)) {
			carro = e.getAcompanhantes().size();
			carroTotal += e.getAcompanhantes().size();
			if (e instanceof ExcursaoTuristica) {
				carro++;
				carroTotal++;
			}
		} else if (e.getMeioTransporte().equals(MeioTransporteEnum.MOTOCICLETA)) {
			motocicleta = e.getAcompanhantes().size();
			motocicletaTotal += e.getAcompanhantes().size();
			if (e instanceof ExcursaoTuristica) {
				motocicleta++;
				motocicletaTotal++;
			}
		} else if (e.getMeioTransporte().equals(MeioTransporteEnum.BICICLETA)) {
			bicicleta = e.getAcompanhantes().size();
			bicicletaTotal += e.getAcompanhantes().size();
			if (e instanceof ExcursaoTuristica) {
				bicicleta++;
				bicicletaTotal++;
			}
		} else if (e.getMeioTransporte().equals(MeioTransporteEnum.A_PE)) {
			a_pe = e.getAcompanhantes().size();
			a_peTotal += e.getAcompanhantes().size();
			if (e instanceof ExcursaoTuristica) {
				a_pe++;
				a_peTotal++;
			}
		} else {
			outro = e.getAcompanhantes().size();
			outroTotal += e.getAcompanhantes().size();
			if (e instanceof ExcursaoTuristica) {
				outro++;
				outroTotal++;
			}
		}
	}

	private void porMunicipio(String nome) {
		if (!municipios.containsKey(nome)) {
			municipios.put(nome, 1);
		} else {
			int oldValue = municipios.get(nome);
			municipios.replace(nome, oldValue, ++oldValue);
		}
	}

	private void porIdade(int aux) {
		// VERIFICANDO IDADE
		if (aux < 6) {
			visitante0_5total++;
			visitante0_5anos++;
		} else if (aux >= 6 && aux <= 12) {
			visitante6_12total++;
			visitante6_12anos++;
		} else if (aux >= 13 && aux <= 20) {
			visitante13_20total++;
			visitante13_20anos++;
		} else if (aux >= 21 && aux <= 30) {
			visitante21_30total++;
			visitante21_30anos++;
		} else if (aux >= 31 && aux <= 50) {
			visitante31_50total++;
			visitante31_50anos++;
		} else {
			visitante51_100total++;
			visitante51_100anos++;
		}
	}

	private void porEscolaridade(EscolaridadeEnum escolaridade) {
		// VERIFICANDO ESCOLARIDADE
		if (escolaridade.equals(EscolaridadeEnum.SEM_ESCOLARIDADE)) {
			sem_escolaridade++;
			sem_escolaridadeTotal++;
		} else if (escolaridade.equals(EscolaridadeEnum.EDUCACAO_INFANTIL)) {
			educacao_infantil++;
			educacao_infantilTotal++;
		} else if (escolaridade.equals(EscolaridadeEnum.ENSINO_FUNDAMENTAL_COMPLETO)
				|| escolaridade.equals(EscolaridadeEnum.ENSINO_FUNDAMENTAL_INCOMPLETO)) {
			ensino_fundamental++;
			ensino_fundamentalTotal++;
		} else if (escolaridade.equals(EscolaridadeEnum.ENSINO_MEDIO_COMPLETO)
				|| escolaridade.equals(EscolaridadeEnum.ENSINO_MEDIO_INCOMPLETO)) {
			ensino_medio++;
			ensino_medioTotal++;
		} else {
			ensino_superior++;
			ensino_superiorTotal++;
		}
	}

	private void porSexo(SexoEnum sexo) {
		if (sexo.equals(SexoEnum.FEMININO))
			mulheres++;
		else
			homens++;
	}

	private int checaIdade(Date nascimento) {
		Date atual = new Date();
		int ano = atual.getYear() - nascimento.getYear();

		if (nascimento.getMonth() < atual.getMonth())
			ano++;
		else if (nascimento.getMonth() == atual.getMonth() && nascimento.getDate() <= atual.getDate())
			ano++;

		return ano;
	}

	private void clean() {

		visitante0_5anos = 0;
		visitante6_12anos = 0;
		visitante13_20anos = 0;
		visitante21_30anos = 0;
		visitante31_50anos = 0;
		visitante51_100anos = 0;

		sem_escolaridade = 0;
		educacao_infantil = 0;
		ensino_fundamental = 0;
		ensino_medio = 0;
		ensino_superior = 0;

		onibus_escolar = 0;
		onibus_turismo = 0;
		carro = 0;
		motocicleta = 0;
		bicicleta = 0;
		a_pe = 0;
		outro = 0;

		homens = 0;
		mulheres = 0;
	}

	private void cleanVariaveisTotal() {
		sb = new StringBuilder();
		municipios = new HashMap<String, Integer>();
		// ESCOLARIDADE RELATORIO FINAL
		sem_escolaridadeTotal = 0;
		educacao_infantilTotal = 0;
		ensino_fundamentalTotal = 0;
		ensino_medioTotal = 0;
		ensino_superiorTotal = 0;

		// MEIO TRANSPORTE RELATORIO FINAL
		onibus_escolarTotal = 0;
		onibus_turismoTotal = 0;
		carroTotal = 0;
		motocicletaTotal = 0;
		bicicletaTotal = 0;
		a_peTotal = 0;
		outroTotal = 0;

		// ATIVIDADES
		video = 0;
		museu = 0;
		trilha_lago = 0;
	}
}