package br.com.vilarica.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.vilarica.model.Acompanhante;
import br.com.vilarica.model.VisitanteMaster;
import br.com.vilarica.util.FilterUtil;

public class VisitanteMasterService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private @Inject FilterUtil filterUtil;
	
	public VisitanteMaster porId(Long id){
		return (VisitanteMaster) filterUtil.porId(Acompanhante.class, id);
	}

}