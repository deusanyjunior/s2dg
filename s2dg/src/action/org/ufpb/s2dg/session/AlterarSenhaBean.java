package org.ufpb.s2dg.session;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.Usuario;

@Name("alterarSenha")
@Scope(ScopeType.PAGE)
public class AlterarSenhaBean {
	
	@In
    Fachada fachada;
	@In
	FacesContext facesContext;
	
	private String senhaAtual;
	private String novaSenha1;
	private String novaSenha2;
	private boolean clicou;
	
	public AlterarSenhaBean(){
		clicou = false;
		senhaAtual = "";
		novaSenha1 = "";
		novaSenha2 = "";
	}
	
	public String botaoPressionado(){
		
		clicou = true;
		
		Usuario usuario = fachada.getUsuario();
		
		if(!novaSenha1.equals(novaSenha2)){
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Senhas diferentes.",null));
			return null;
		}
		else if(!Utils.validatePassword(senhaAtual, usuario)){
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Senha inválida.",null));
			return null;
		}
		
		usuario.setSenha(Utils.generateHash(novaSenha1));
		
		fachada.atualizaUsuario(usuario);
		
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Senha alterada com sucesso.",null));
		
		return "home";
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha1() {
		return novaSenha1;
	}

	public void setNovaSenha1(String novaSenha1) {
		this.novaSenha1 = novaSenha1;
	}

	public String getNovaSenha2() {
		return novaSenha2;
	}

	public void setNovaSenha2(String novaSenha2) {
		this.novaSenha2 = novaSenha2;
	}

	public boolean isClicou() {
		return clicou;
	}

	public void setClicou(boolean clicou) {
		this.clicou = clicou;
	}
	
	public String cancelar() {
		return "home";
	}
	
}
