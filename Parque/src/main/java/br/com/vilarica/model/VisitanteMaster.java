package br.com.vilarica.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class VisitanteMaster implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@NotBlank
	@Column(length = 100, nullable = false)
	private String nome;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento")
	private Date dataNascimento;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private SexoEnum sexo;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private EscolaridadeEnum escolaridade;
	
	@NotNull
	@OneToOne
	private @Inject Contato contato;
	
	@OneToOne
	@NotNull
	private @Inject Endereco endereco;
	
	@NotNull
	@OneToOne
	private @Inject Municipio municipio;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Acompanhante> acompanhantes = new ArrayList<Acompanhante>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public SexoEnum getSexo() {
		return sexo;
	}

	public void setSexo(SexoEnum sexo) {
		this.sexo = sexo;
	}

	public EscolaridadeEnum getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(EscolaridadeEnum escolaridade) {
		this.escolaridade = escolaridade;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public List<Acompanhante> getAcompanhantes() {
		return acompanhantes;
	}

	public void setAcompanhantes(List<Acompanhante> acompanhantes) {
		this.acompanhantes = acompanhantes;
	}

	@Override
	public String toString() {
		return "VisitanteMaster [id=" + id + ", nome=" + nome
				+ ", dataNascimento=" + dataNascimento + ", sexo=" + sexo
				+ ", escolaridade=" + escolaridade + ", \ncontato=" + contato
				+ ", \nendereco=" + endereco + ", \nmunicipio=" + municipio
				+ ", acompanhantes=" + acompanhantes + "]";
	}
	
}
