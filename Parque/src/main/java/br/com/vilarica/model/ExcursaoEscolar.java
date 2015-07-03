package br.com.vilarica.model;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ExcursaoEscolar extends Excursao implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@OneToOne
	private @Inject Instituicao instituicao;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private StatusExcursao status;
	

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public StatusExcursao getStatus() {
		return status;
	}

	public void setStatus(StatusExcursao status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ExcursaoEscolar [\ninstituicao=" + instituicao + ", status="
				+ status + "]";
	}

}
