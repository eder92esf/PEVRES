package br.com.vilarica.security;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.com.vilarica.model.Usuario;
import br.com.vilarica.service.UsuarioService;
import br.com.vilarica.util.Temporizador;

@Named
@RequestScoped
public class Seguranca {

	private @Inject UsuarioService controller;
	
	private static boolean temporizador = false;
	private String senhaAtual;
	private String senhaNova;
	private String confirmaSenhaNova;

	@Inject
	private ExternalContext externalContext;

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}

	public String getConfirmaSenhaNova() {
		return confirmaSenhaNova;
	}

	public void setConfirmaSenhaNova(String confirmaSenhaNova) {
		this.confirmaSenhaNova = confirmaSenhaNova;
	}

	public String getNomeUsuario() {
		String nome = null;

		UsuarioSistema usuarioLogado = getUsuarioLogado();

		if (usuarioLogado != null) {
			nome = usuarioLogado.getUsuario().getNome();
		}

		return nome;
	}

	@Produces
	@UsuarioLogado
	public UsuarioSistema getUsuarioLogado() {
		UsuarioSistema usuario = null;

		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
				.getCurrentInstance().getExternalContext().getUserPrincipal();

		if (auth != null && auth.getPrincipal() != null) {
			usuario = (UsuarioSistema) auth.getPrincipal();
			if(!temporizador){
				Temporizador t = new Temporizador();
				t.createTemporizador();
				temporizador = true;
			}
		}
		return usuario;
	}

	public void updateData() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;

		Usuario u = getUsuarioLogado().getUsuario();
		String retorno = this.controller.saveOrUpdate(u);

		if (retorno.equals("")) {
			msg = new FacesMessage("Dados atualizados com sucesso!");
		} else {
			msg = new FacesMessage(retorno);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		context.addMessage(null, msg);
	}

	public void updatePassword() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;

		Usuario u = getUsuarioLogado().getUsuario();

		if (u.getSenha().equals(senhaAtual)) {
			if (senhaNova.equals(confirmaSenhaNova)) {
				u.setSenha(senhaNova);
				String retorno = this.controller.updatePassword(u);
				if (retorno.equals("")) {
					msg = new FacesMessage("Dados atualizados com sucesso!");
				} else {
					msg = new FacesMessage(retorno);
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				}
			}else{
				msg = new FacesMessage("Nova senha não confere!");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			}
		} else {
			msg = new FacesMessage("Senha atual inválida!");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		context.addMessage(null, msg);
	}

	// Exemplos
	// Permissoes para proteger ou não componentes das páginas
	// Nesse caso se é Administrador ou Vendedor pode Emitir ou Cancelar Pedidos
	/*
	 * public boolean isEmitirPedidoPermitido() { return
	 * externalContext.isUserInRole("ADMINISTRADORES") ||
	 * externalContext.isUserInRole("VENDEDORES"); }
	 * 
	 * public boolean isCancelarPedidoPermitido() { return
	 * externalContext.isUserInRole("ADMINISTRADORES") ||
	 * externalContext.isUserInRole("VENDEDORES"); }
	 * 
	 * public boolean isAdministrador(){ return
	 * externalContext.isUserInRole("ADMINISTRADORES"); }
	 */

	// Exemplo de como usar no componente:
	// <p:commandButton value="Emitir"
	// action="#{emissaoPedidoBean.emitirPedido}"
	// process="@form" update="@form"
	// disabled="#{cadastroPedidoBean.pedido.naoEmissivel or not
	// seguranca.emitirPedidoPermitido}" />

}
