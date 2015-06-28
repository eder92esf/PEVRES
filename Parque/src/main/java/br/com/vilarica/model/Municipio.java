package br.com.vilarica.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Municipio implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotBlank
	@Column(nullable = false, length = 50)
	private String nome;
	
	@Column(length = 10)
	private String cep;
	
	@Enumerated(EnumType.STRING)
	private EstadoEnum estado;
	
	@Enumerated(EnumType.STRING)
	private PaisEnum pais;
	
	@OneToMany(targetEntity = Instituicao.class, orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Instituicao> instituicoes = new ArrayList<Instituicao>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EstadoEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoEnum estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public PaisEnum getPais() {
		return pais;
	}

	public void setPais(PaisEnum pais) {
		this.pais = pais;
	}

	public List<Instituicao> getInstituicoes() {
		return instituicoes;
	}

	public void setInstituicoes(List<Instituicao> instituicoes) {
		this.instituicoes = instituicoes;
	}

	@Override
	public String toString() {
		return "Municipio [id=" + id + ", estado=" + estado + ", cep=" + cep
				+ ", nome=" + nome + ", pais=" + getPais() + "]";
	}
	
}
