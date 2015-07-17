package br.com.vilarica.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import br.com.vilarica.model.Acompanhante;
import br.com.vilarica.model.ExcursaoEscolar;
import br.com.vilarica.model.Guia;
import br.com.vilarica.model.Instituicao;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.service.ExcursaoService;
import br.com.vilarica.util.LeitorCSV;

@Named
@ViewScoped
public class AgendaExcursaoEscolarBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject ExcursaoEscolar excursaoEscolar;
	private @Inject ExcursaoService controller;
	private @Inject Acompanhante acompanhante;
	private @Inject Acompanhante selecionado;
	private @Inject LeitorCSV leitor;
	private @Inject Guia guia;
	private UploadedFile file;
	private boolean isAgendavel = false;
	private List<Acompanhante> lista;

	public ExcursaoService getController() {
		return controller;
	}

	public ExcursaoEscolar getExcursaoEscolar() {
		return excursaoEscolar;
	}

	public void setExcursaoEscolar(ExcursaoEscolar excursaoEscolar) {
		this.excursaoEscolar = excursaoEscolar;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
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

	public void agendar() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;

		this.controller.listExcursoesPorGuia(excursaoEscolar.getDataExcursao(),
				excursaoEscolar.getGuia().getId());
		
		this.isAgendavel = this.controller.agendar(excursaoEscolar
				.getDataExcursao());

		if (this.isAgendavel) {
			if (lista != null) {
				for (Acompanhante acompanhante : lista) {
					acompanhante.setMunicipio(this.excursaoEscolar
							.getInstituicao().getMunicipio());
				}
			}
			String retorno = this.controller.agendaExcursaoEscolar(excursaoEscolar);
			if (retorno.equals("")) {
				msg = new FacesMessage("Excursão escolar agendada com sucesso.");
				msg.setDetail("");
			} else {
				msg = new FacesMessage("Erro ao agendar excursão escolar.");
				msg.setDetail(retorno);
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			}
			System.out.println("\n\nEXCURSAO AO SALVAR\n" + excursaoEscolar);
			excursaoEscolar = new ExcursaoEscolar();
			excursaoEscolar.setAcompanhantes(new ArrayList<Acompanhante>());
			excursaoEscolar.setGuia(new Guia());
			excursaoEscolar.setInstituicao(new Instituicao());
		} else {
			msg = new FacesMessage("Não é possível agendar a excursão!");
			msg.setDetail("Selecione uma nova data e/ou horário para este guia.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		context.addMessage(null, msg);
	}

	public void onDateSelect(SelectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;

		this.controller.listExcursoesEscolar(excursaoEscolar.getDataExcursao());
		this.controller.desabilitaAtividade(excursaoEscolar.getDataExcursao());

		/*
		 * if (!this.isAgendavel) { msg = new
		 * FacesMessage("Não é possível agendar a excursão!");
		 * msg.setDetail("Selecione uma nova data e/ou horário para este guia."
		 * ); msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		 * context.addMessage(null, msg); }
		 */
	}

	public List<Municipio> filtrarMunicipio(String consulta) {
		return this.controller.filtarMunicipios(consulta);
	}

	public List<Guia> filtrarGuia(String consulta) {
		return this.controller.filtrarGuias(consulta);
	}

	public List<Instituicao> filtrarInstituicoes(String consulta) {
		return this.controller.filtrarInstituicoes(consulta);
	}

	public void addAcompanhante() {
		this.excursaoEscolar.getAcompanhantes().add(acompanhante);
		acompanhante = new Acompanhante();
	}

	public void removeAcompanhante() {
		for (Acompanhante a : this.excursaoEscolar.getAcompanhantes()) {
			if (a.equals(selecionado)) {
				this.excursaoEscolar.getAcompanhantes().remove(selecionado);
				break;
			}
		}
	}

	public void upload() {
		if (this.file != null) {
			try {
				String arquivo = leitor.gravar(this.file.getInputstream(),
						this.file.getSize());
				File temp = new File(arquivo);
				lista = leitor.ler(temp);
				this.excursaoEscolar.setAcompanhantes(lista);
				temp.delete();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

}
