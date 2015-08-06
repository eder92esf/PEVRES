package br.com.vilarica.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.jboss.weld.bean.builtin.FacadeInjectionPoint;
import org.omnifaces.util.Faces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.vilarica.service.RelatorioService;

import com.sun.istack.internal.NotNull;

@Named
@ViewScoped
public class RelatorioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject RelatorioService controller;
	private File download = null;
	private StreamedContent file;

	@NotNull
	private Date inicio;
	@NotNull
	private Date fim;

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public StreamedContent getFile() throws FileNotFoundException {
		if(download == null){
			FacesContext fc = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage("Gere um relatório antes de efetuar download.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(null, msg);
			return null;
		}
		InputStream stream = new FileInputStream(download);
		file = new DefaultStreamedContent(stream, "application/csv",
				this.download.getName());
		this.download = null;
		return file;
	}

	public void gerar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		try {
			this.download = this.controller.gerar(this.inicio, this.fim);
			if(this.download != null){
				
				msg = new FacesMessage("Relatório criado com sucesso!");
			}else{
				msg = new FacesMessage("Erro ao gerar relatório!");
				msg.setDetail("");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			}
			fc.addMessage(null, msg);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}