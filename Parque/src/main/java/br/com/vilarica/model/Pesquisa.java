package br.com.vilarica.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Pesquisa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Inject
	@OneToOne
	@NotNull
	private Instituicao instituicao;
	
	//@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "inicio_pesquisa", nullable = false)
	private Calendar inicioPesquisa;
	
	//@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "fim_pesquisa", nullable = false)
	private Calendar fimPesquisa;
	
	//@NotBlank
	@Column(name = "tema_pesquisa" , nullable = false, length = 60)
	private String temaPesquisa;
	
	//@NotBlank
	@Column(name = "descricao_pesquisa", nullable = false, length = 255)
	private String descricaoPesquisa;
	
	//@NotEmpty
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VisitanteMaster> pesquisadores = new ArrayList<VisitanteMaster>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Calendar getInicioPesquisa() {
		return inicioPesquisa;
	}

	public void setInicioPesquisa(Calendar inicioPesquisa) {
		this.inicioPesquisa = inicioPesquisa;
	}

	public Calendar getFimPesquisa() {
		return fimPesquisa;
	}

	public void setFimPesquisa(Calendar fimPesquisa) {
		this.fimPesquisa = fimPesquisa;
	}

	public String getTemaPesquisa() {
		return temaPesquisa;
	}

	public void setTemaPesquisa(String temaPesquisa) {
		this.temaPesquisa = temaPesquisa;
	}

	public String getDescricaoPesquisa() {
		return descricaoPesquisa;
	}

	public void setDescricaoPesquisa(String descricaoPesquisa) {
		this.descricaoPesquisa = descricaoPesquisa;
	}

	public List<VisitanteMaster> getPesquisadores() {
		return pesquisadores;
	}

	public void setPesquisadores(List<VisitanteMaster> pesquisadores) {
		this.pesquisadores = pesquisadores;
	}

	@Override
	public String toString() {
		return "Pesquisa [id=" + id + ", \n\tinstituicao=" + instituicao
				+ ", inicioPesquisa=" + inicioPesquisa + ", fimPesquisa="
				+ fimPesquisa + ", temaPesquisa=" + temaPesquisa
				+ ", descricaoPesquisa=" + descricaoPesquisa
				+ ", pesquisadores=" + pesquisadores + "]";
	}

}
