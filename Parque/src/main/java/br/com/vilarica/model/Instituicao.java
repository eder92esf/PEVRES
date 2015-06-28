package br.com.vilarica.model;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.com.vilarica.annotations.Sigla;

@Entity
public class Instituicao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Sigla
	@NotBlank
	@Column(length = 10, nullable = false)
	private String sigla;
	
	@NotBlank
	@Column(length = 100, nullable = false)
	private String nome;
	
	@NotNull
	@OneToOne
	@JoinColumn(nullable = false)
	private @Inject Contato contato;
	
	@NotNull
	@OneToOne
	@JoinColumn(nullable = false)
	private @Inject Endereco endereco;
	
	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private @Inject Municipio municipio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	@Override
	public String toString() {
		return "Instituicao [id=" + id + ", sigla=" + sigla
				+ ", nome=" + nome + ",\ncontato=" + contato + ",\nendereco="
				+ endereco + ",\nmunicipio=" + municipio + "]";
	}
	
}
