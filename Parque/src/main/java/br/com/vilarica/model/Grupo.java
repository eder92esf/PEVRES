package br.com.vilarica.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "grupo")
public class Grupo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false, length=40)
	private String nome;
	
	@Column(nullable=false, length=80)
	private String descricao;
	
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	    public boolean equals(Object other) {
	        return (other instanceof Grupo) && (id != null)
	            ? id.equals(((Grupo) other).id)
	            : (other == this);
	    }

	    @Override
	    public int hashCode() {
	        return (id != null)
	            ? (this.getClass().hashCode() + id.hashCode())
	            : super.hashCode();
	    }

	 @Override
	 public String toString() {
	     return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
	 }

}
