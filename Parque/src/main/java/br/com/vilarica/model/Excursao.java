package br.com.vilarica.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class Excursao implements Serializable{

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
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TipoAtividadeExcursao> atividades = new ArrayList<TipoAtividadeExcursao>();
	
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

	public MeioTransporteEnum getMeioTransporte() {
		return MeioTransporte;
	}

	public void setMeioTransporte(MeioTransporteEnum meioTransporte) {
		MeioTransporte = meioTransporte;
	}

	public List<TipoAtividadeExcursao> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<TipoAtividadeExcursao> atividades) {
		this.atividades = atividades;
	}

	public List<Acompanhante> getAcompanhantes() {
		return acompanhantes;
	}

	public void setAcompanhantes(List<Acompanhante> acompanhantes) {
		this.acompanhantes = acompanhantes;
	}

	@Override
	public String toString() {
		return "Excursao [id=" + id + ", dataExcursao=" + dataExcursao
				+ ", MeioTransporte=" + MeioTransporte + ", atividades="
				+ atividades + ", \nacompanhantes=\n\t" + acompanhantes + "]\n";
	}
}
