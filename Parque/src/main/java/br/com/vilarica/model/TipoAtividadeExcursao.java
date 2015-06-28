package br.com.vilarica.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TipoAtividadeExcursao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private TipoAtividadeEnum atividadeEnum;

	public TipoAtividadeEnum getAtividadeEnum() {
		return atividadeEnum;
	}

	public void setAtividadeEnum(TipoAtividadeEnum atividadeEnum) {
		this.atividadeEnum = atividadeEnum;
	}

}
