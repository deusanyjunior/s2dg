package org.ufpb.s2dg.session;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Credentials;

@Name("alterarSenha")
public class AlterarSenhaBean {
	
	@In 
    Credentials credentials;
	
	@In
    Fachada fachada;
	
	private String senhaAtual;
	private String novaSenha1;
	private String novaSenha2;
	private boolean clicou;
	
	public AlterarSenhaBean(){
		clicou = false;
	}
	
	public String botaoPressionado(){
		clicou = true;
		return null;
	}
	
	public String fazerAlteracao(){
		if(!novaSenha1.equals(novaSenha2)){
			return "Senhas diferentes";
		}
		else if(!senhaAtual.equals(credentials.getPassword())){
			return "Senha inválida (Falta implementar)";
		}
		
		fachada.alteraSenha(credentials.getUsername(), senhaAtual, novaSenha1);
		
		return "Senha alterada com sucesso!";
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
}
