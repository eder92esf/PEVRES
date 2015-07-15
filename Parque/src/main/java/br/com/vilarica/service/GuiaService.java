package br.com.vilarica.service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.vilarica.jpa.Transactional;
import br.com.vilarica.model.EscolaridadeEnum;
import br.com.vilarica.model.Guia;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.model.SexoEnum;
import br.com.vilarica.util.FilterUtil;

public class GuiaService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private @Inject EntityManager manager;
	private @Inject FilterUtil filterUtil;
	private List<Guia> guias;
	private List<EscolaridadeEnum> escolaridades;
	private List<SexoEnum> sexos;
	
	@PostConstruct
	private void init(){
		if(escolaridades == null)
			escolaridades = EscolaridadeEnum.getEscolaridades();
		if(sexos == null)
			sexos = SexoEnum.getSexos();
	}
	
	public List<Guia> getGuias(){
		return guias;
	}
	
	public Date getDataAtual(){
		return Calendar.getInstance().getTime();
	}
	
	public List<EscolaridadeEnum> getEscolaridades() {
		return escolaridades;
	}

	public List<SexoEnum> getSexos() {
		return sexos;
	}

	@Transactional
	public String save(Guia guia){
		try {
			if(guia.getId() == null){
				manager.persist(guia.getEndereco());
				manager.persist(guia.getContato());
				manager.persist(guia);
			}else{
				manager.merge(guia);
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void filtarGuias(String consulta){
		guias = filterUtil.filtarGuias(consulta); 
	}
	
	@SuppressWarnings("unchecked")
	public List<Municipio> filtarMunicipios(String consulta) {
		return filterUtil.filtarMunicipios(consulta, null);
	}
	
	public Guia porId(Long id){
		return (Guia) filterUtil.porId(Guia.class, id);
	}
}
