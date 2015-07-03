package br.com.vilarica.model;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ExcursaoTuristica extends Excursao implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@OneToOne
	@JoinColumn(name = "visitante_master", nullable = false)
	private @Inject VisitanteMaster visitanteMaster;
	


	public VisitanteMaster getVisitanteMaster() {
		return visitanteMaster;
	}

	public void setVisitanteMaster(VisitanteMaster visitanteMaster) {
		this.visitanteMaster = visitanteMaster;
	}

	@Override
	public String toString() {
		return "ExcursaoTuristica [\nvisitanteMaster=" + visitanteMaster + "]";
	}

}
