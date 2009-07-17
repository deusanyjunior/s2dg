package org.ufpb.s2dg.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("alterarSenha")
@Scope(ScopeType.PAGE)
public class AlterarSenhaBean {
	
	@In
    Fachada fachada;
	
	private String senhaAtual;
	private String novaSenha1;
	private String novaSenha2;
	private boolean clicou;
	private String msg;
	
	public AlterarSenhaBean(){
		clicou = false;
		senhaAtual = "";
		novaSenha1 = "";
		novaSenha2 = "";
		msg = "";
	}
	
	public String botaoPressionado(){
		
		clicou = true;
		
		if(!novaSenha1.equals(novaSenha2)){
			msg = "Senhas diferentes";
			return null;
		}
		else if(!senhaAtual.equals(fachada.getUsuario().getSenha())){
			msg = "Senha inválida";
			return null;
		}
		
		fachada.alteraSenha(fachada.getUsuario().getCpf(), senhaAtual, novaSenha1);
		
		msg = "Senha alterada com sucesso!";
		
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

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
	
}
