package br.com.vilarica.controller;

import java.io.Serializable;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import br.com.vilarica.service.GraficosService;

@Named
@RequestScoped
public class GraficosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject GraficosService controller;
	private LineChartModel model;

	public void preRender() {
		this.controller.setNumeroDias(90);
		this.model = new LineChartModel();
		this.model.setAnimate(true);
		this.model.setTitle("Visitantes nos últimos 90 dias.");
		this.model.setLegendPosition("e");
		adicionarSerie();
		Axis yAxis = this.model.getAxis(AxisType.Y);
		Axis xAxis = this.model.getAxis(AxisType.X);
		yAxis.setLabel("Total Visitantes");
		yAxis.setMin(0);
		xAxis.setLabel("Mês");
		xAxis.setMax(12);
	}

	private void adicionarSerie() {
		this.controller.gerarGraficos();

		LineChartSeries line = new LineChartSeries();
		line.setLabel("Visitantes");

		Set<Integer> keys = this.controller.getPorVisitantes().keySet();
		for (int chave : keys) {
			line.set(chave, this.controller.getPorVisitantes().get(chave));
		}
		this.model.addSeries(line);
	}

	public CartesianChartModel getModel() {
		return model;
	}

}
