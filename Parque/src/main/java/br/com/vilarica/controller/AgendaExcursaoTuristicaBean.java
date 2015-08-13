package br.com.vilarica.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vilarica.model.Acompanhante;
import br.com.vilarica.model.Contato;
import br.com.vilarica.model.Endereco;
import br.com.vilarica.model.ExcursaoTuristica;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.model.VisitanteMaster;
import br.com.vilarica.service.ExcursaoService;

@Named
@ViewScoped
public class AgendaExcursaoTuristicaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject ExcursaoTuristica excursao;
	private @Inject Acompanhante acompanhante;
	private @Inject Acompanhante selecionado;
	private @Inject ExcursaoService controller;

	public ExcursaoTuristica getExcursao() {
		return excursao;
	}

	public void setExcursao(ExcursaoTuristica excursao) {
		this.excursao = excursao;
	}

	public ExcursaoService getController() {
		return controller;
	}

	public Acompanhante getAcompanhante() {
		return acompanhante;
	}

	public void setAcompanhante(Acompanhante acompanhante) {
		this.acompanhante = acompanhante;
	}

	public Acompanhante getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Acompanhante selecionado) {
		this.selecionado = selecionado;
	}

	public List<Municipio> filtrarMunicipio(String consulta) {
		return this.controller.filtarMunicipios(consulta);
	}

	// public void onDateSelect(SelectEvent event) {
	public void onDateSelect(AjaxBehaviorEvent event) {
		Date dataNascimento = (Date) ((UIOutput) event.getSource()).getValue();
		// this.controller.checaImputavel(this.excursao.getVisitanteMaster().getDataNascimento());
		this.controller.checaImputavel(dataNascimento);
	}

	public void onChangeSelect(AjaxBehaviorEvent event) {
		String cpf = ((String) ((UIOutput) event.getSource()).getValue());
		StringBuilder sb = new StringBuilder(cpf.replace(".", "").replaceAll("-", ""));

		boolean isCpfValido = this.controller.isCPFValido(sb.toString());
		if (isCpfValido) {
			VisitanteMaster visitanteMaster = this.controller.getVisitanteMasterByCPF(sb.toString());
			if (visitanteMaster != null) {
				excursao.setVisitanteMaster(visitanteMaster);
				this.controller.checaImputavel(visitanteMaster.getDataNascimento());
			}
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage("CPF informado inválido!");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			msg.setDetail("");
			fc.addMessage(null, msg);
		}
	}

	public void onChangeLabel(AjaxBehaviorEvent event) {
		String cpf = ((String) ((UIOutput) event.getSource()).getValue());

		if (cpf.length() == 11) {
			boolean isCpfValido = this.controller.isCPFValido(cpf);
			if (isCpfValido) {
				acompanhante.setCPF_RG(cpf);
			} else {
				FacesContext fc = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage("CPF informado inválido!");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				msg.setDetail("");
				fc.addMessage(null, msg);
			}
		}
	}

	public void addAcompanhante() {
		this.excursao.getAcompanhantes().add(acompanhante);
		acompanhante = new Acompanhante();
	}

	public void removeAcompanhante() {
		for (Acompanhante a : this.excursao.getAcompanhantes()) {
			if (a.equals(selecionado)) {
				this.excursao.getAcompanhantes().remove(a);
				break;
			}
		}
	}

	public void checkin() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		StringBuilder sb = new StringBuilder(
				excursao.getVisitanteMaster().getCpf().replace(".", "").replaceAll("-", ""));
		excursao.getVisitanteMaster().setCpf(sb.toString());

		String retorno = this.controller.checkinExcursaoTuristica(excursao);

		if (retorno.equals("")) {
			msg = new FacesMessage("Check-in de excursão efetuado com sucesso.");
			msg.setDetail("");
		} else {
			msg = new FacesMessage("Erro ao efetuar check-in de excursão.");
			msg.setDetail(retorno);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		context.addMessage(null, msg);

		excursao = new ExcursaoTuristica();
		excursao.setAcompanhantes(new ArrayList<Acompanhante>());
		excursao.setVisitanteMaster(new VisitanteMaster());
		excursao.getVisitanteMaster().setContato(new Contato());
		excursao.getVisitanteMaster().setEndereco(new Endereco());
		excursao.getVisitanteMaster().setMunicipio(new Municipio());
	}

}