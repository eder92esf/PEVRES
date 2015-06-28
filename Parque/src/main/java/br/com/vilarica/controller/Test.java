package br.com.vilarica.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class Test implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Calendar data;
	private Date dataAtual;
	
	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Date getDataAtual() {
		dataAtual = Calendar.getInstance().getTime();
		return dataAtual;
	}

	public void print(){
		System.out.println("\nCHAMOU O METODO");
		System.out.println(getData().getTime().toString());
	}
}
