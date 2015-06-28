package br.com.vilarica.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class ExcursaoEscolar implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@OneToOne
	private @Inject Instituicao instituicao;

	@NotNull
	@Temporal(TemporalType.DATE)
	private Calendar dataExcursao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private MeioTransporteEnum MeioTransporte;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Acompanhante> acompanhantes = new ArrayList<Acompanhante>();

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getDataExcursao() {
		return dataExcursao;
	}

	public void setDataExcursao(Calendar dataExcursao) {
		this.dataExcursao = dataExcursao;
	}

	public MeioTransporteEnum getMeioTransporte() {
		return MeioTransporte;
	}

	public void setMeioTransporte(MeioTransporteEnum meioTransporte) {
		MeioTransporte = meioTransporte;
	}

	public List<Acompanhante> getAcompanhantes() {
		return acompanhantes;
	}

	public void setAcompanhantes(List<Acompanhante> acompanhantes) {
		this.acompanhantes = acompanhantes;
	}

	@Override
	public String toString() {
		return "ExcursaoEscolar [id=" + id + ", \ninstituicao=" + instituicao
				+ ", dataExcursao=" + dataExcursao + ", MeioTransporte="
				+ MeioTransporte + ", acompanhantes=" + acompanhantes + "]";
	}

}
