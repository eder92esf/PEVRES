package br.com.vilarica.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.vilarica.model.Usuario;
import br.com.vilarica.util.FilterUtil;

public class LoginService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/*
	private @Inject FilterUtil filterUtil;
	
	public Usuario getUserByLogin(String login){
		return filterUtil.getUserByLogin(login);
	}
	
	public boolean userExits(){
		return filterUtil.usersExits();
	}
	*/

}
