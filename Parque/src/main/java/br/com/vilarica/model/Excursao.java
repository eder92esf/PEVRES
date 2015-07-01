package br.com.vilarica.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Excursao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataExcursao;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private MeioTransporteEnum MeioTransporte;

	@NotNull
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TipoAtividadeExcursao> atividades = new ArrayList<TipoAtividadeExcursao>();
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "visitante_master", nullable = false)
	private @Inject VisitanteMaster visitanteMaster;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Acompanhante> acompanhantes = new ArrayList<Acompanhante>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataExcursao() {
		return dataExcursao;
	}

	public void setDataExcursao(Date dataExcursao) {
		this.dataExcursao = dataExcursao;
	}

	public VisitanteMaster getVisitanteMaster() {
		return visitanteMaster;
	}

	public void setVisitanteMaster(VisitanteMaster visitanteMaster) {
		this.visitanteMaster = visitanteMaster;
	}

	public List<Acompanhante> getAcompanhantes() {
		return acompanhantes;
	}

	public void setAcompanhantes(List<Acompanhante> acompanhantes) {
		this.acompanhantes = acompanhantes;
	}

	public MeioTransporteEnum getMeioTransporte() {
		return MeioTransporte;
	}

	public void setMeioTransporte(MeioTransporteEnum meioTransporte) {
		MeioTransporte = meioTransporte;
	}

	public List<TipoAtividadeExcursao> getAtividades() {
		return atividades;
	}

	@Override
	public String toString() {
		return "Excursao [id=" + id + ", dataExcursao=" + dataExcursao
				+ ", MeioTransporte=" + MeioTransporte + ", \natividades="
				+ atividades + ", \nvisitanteMaster=" + visitanteMaster
				+ ", \nacompanhantes=" + acompanhantes + "]";
	}
}
