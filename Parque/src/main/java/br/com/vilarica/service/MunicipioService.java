package br.com.vilarica.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.vilarica.jpa.Transactional;
import br.com.vilarica.model.EstadoEnum;
import br.com.vilarica.model.Municipio;
import br.com.vilarica.model.PaisEnum;

public class MunicipioService implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject EntityManager manager;
	private List<EstadoEnum> estados;
	private List<PaisEnum> paises;
	private List<Municipio> municipios;

	public List<EstadoEnum> getEstados() {
		return estados;
	}

	public List<PaisEnum> getPaises() {
		return paises;
	}

	public List<Municipio> getMunicipios() {
		return municipios;
	}

	@PostConstruct
	public void init() {
		System.out.println("\n\nCHAMOU INIT");
		if (estados == null) {
			estados = EstadoEnum.getEstados();
		}
		if (paises == null) {
			paises = PaisEnum.getPaises();
		}
	}

	public Municipio porId(Long value) {
		return this.manager.find(Municipio.class, value);
	}

	@Transactional
	public String save(Municipio municipio) {
		try {
			if (municipio.getId() != null) {
				this.manager.merge(municipio);
			} else {
				this.manager.persist(municipio);
			}
			municipio = new Municipio();
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	@SuppressWarnings("unchecked")
	public void filter(String nome, EstadoEnum estado){
		municipios = new ArrayList<Municipio>();
		Session s = this.manager.unwrap(Session.class);
		Criteria c = s.createCriteria(Municipio.class);
		
		if(nome != null && nome.trim().length() > 0){
			c.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}
		if(estado == null){
			estado = EstadoEnum.PR;
		}
		c.add(Restrictions.eq("estado", estado));
		
		municipios = c.addOrder(Order.asc("nome")).list();
	}
}
