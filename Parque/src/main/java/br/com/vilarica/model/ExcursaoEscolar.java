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
	private Integer totalVisitantes;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private StatusExcursao status;
	
	@NotNull
	@OneToOne
	private @Inject Guia guia;

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Integer getTotalVisitantes() {
		return totalVisitantes;
	}

	public void setTotalVisitantes(Integer totalVisitantes) {
		this.totalVisitantes = totalVisitantes;
	}

	public StatusExcursao getStatus() {
		return status;
	}

	public void setStatus(StatusExcursao status) {
		this.status = status;
	}

	public Guia getGuia() {
		return guia;
	}

	public void setGuia(Guia guia) {
		this.guia = guia;
	}

	@Override
	public String toString() {
		return "ExcursaoEscolar ["+ super.toString() +" \ninstituicao=" + instituicao + ", status="
				+ status + "]";
	}

}
