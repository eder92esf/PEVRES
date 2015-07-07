package br.com.vilarica.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import br.com.vilarica.model.Acompanhante;
import br.com.vilarica.model.ExcursaoEscolar;
import br.com.vilarica.model.Instituicao;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.service.AgendaExcursaoService;
import br.com.vilarica.util.LeitorCSV;

@Named
@ViewScoped
public class AgendaExcursaoEscolarBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject ExcursaoEscolar excursaoEscolar;
	// private @Inject AgendaExcursaoEscolarService controller;
	private @Inject Acompanhante acompanhante;
	private @Inject Acompanhante selecionado;
	private @Inject AgendaExcursaoService controller;
	private UploadedFile file;
	private @Inject LeitorCSV leitor;

	public AgendaExcursaoService getController() {
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

	public List<Instituicao> filtrarInstituicoes(String consulta) {
		return this.controller.filtrarInstituicoes(consulta);
	}

	public void agendar() {
		this.controller.agendaExcursaoEscolar(excursaoEscolar);
	}

	public void onDateSelect(SelectEvent event) {
		this.controller.desabilitaAtividade(excursaoEscolar.getDataExcursao());
	}

	public List<Municipio> filtrarMunicipio(String consulta) {
		return this.controller.filtarMunicipios(consulta);
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
				String arquivo = leitor.gravar(this.file.getInputstream(), this.file.getSize());
				File temp = new File(arquivo);
				List<Acompanhante> lista = leitor.ler(temp, this.excursaoEscolar.getInstituicao().getMunicipio());
				this.excursaoEscolar.setAcompanhantes(lista);
				temp.delete();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

}
