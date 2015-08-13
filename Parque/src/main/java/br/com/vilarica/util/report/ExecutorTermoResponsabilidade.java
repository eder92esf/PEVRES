package br.com.vilarica.util.report;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.jdbc.Work;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class ExecutorTermoResponsabilidade implements Work {

	private String caminhoRetorio;
	private HttpServletResponse response;
	private Map<String, Object> parametros;
	private String nomeArquivo;

	public ExecutorTermoResponsabilidade(String caminhoRelatorio, HttpServletResponse response,
			Map<String, Object> parametros, String nomeArquivo) {
		super();
		this.caminhoRetorio = caminhoRelatorio;
		this.response = response;
		this.parametros = parametros;
		this.nomeArquivo = nomeArquivo;
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		try {
			InputStream relatorioStream = this.getClass().getResourceAsStream(caminhoRetorio);
			JasperPrint print = JasperFillManager.fillReport(relatorioStream, parametros, connection);
			JRExporter exportador = new JRPdfExporter();
			exportador.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
			exportador.setParameter(JRExporterParameter.JASPER_PRINT, print);

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition",
					"attachment;filename=\"" + this.nomeArquivo + "\"");

			exportador.exportReport();
		} catch (Exception e) {
			throw new SQLException("Erro ao executar relatório ", e);
		}
	}

}
