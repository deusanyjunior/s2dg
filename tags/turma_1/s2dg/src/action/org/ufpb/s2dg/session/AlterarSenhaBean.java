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
	
	@In
	MenuAction MenuAction;
	
	private String senhaAtual;
	private String novaSenha1;
	private String novaSenha2;
	private boolean clicou;
	
	private String pergunta;
	private String novaResposta1;
	private String novaResposta2;
	
	public AlterarSenhaBean(){
		clicou = false;
		senhaAtual = "";
		novaSenha1 = "";
		novaSenha2 = "";
		pergunta = "";
		novaResposta1 = "";
		novaResposta2 = "";
	}
	
	public void botaoPressionado(){
		
		clicou = true;
		
		Usuario usuario = fachada.getUsuario();
		
		if(!novaSenha1.equals(novaSenha2)){
			facesContext.addMessage("homepanel", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Senhas diferentes.",null));
		}
		else if(!Utils.validatePassword(senhaAtual, usuario)){
			facesContext.addMessage("homepanel", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Senha inválida.",null));
		}else {		
			usuario.setSenha(Utils.generateHash(novaSenha1));		
			fachada.atualizaUsuario(usuario);		
			String log = "Usuario "+usuario.getNome()
				+" (CPF:"+usuario.getCpf()
				+") alterou a senha com sucesso.";
			fachada.fazLog(log);
						facesContext.addMessage("homepanel", new FacesMessage(FacesMessage.SEVERITY_INFO,"Senha alterada com sucesso.",null));		
		}
		
	}
	
public void botaoPressionado2(){
		
		clicou = true;
		
		Usuario usuario = fachada.getUsuario();
		
		if(!novaResposta1.equals(novaResposta2)){
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Respostas diferentes.",null));
		}else {		
			usuario.setPergunta(pergunta);
			usuario.setResposta(novaResposta1);
			
			fachada.atualizaUsuario(usuario);		
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Senha alterada com sucesso.",null));
		}
		
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}
	
	public String getPergunta() {
		return pergunta;
	}
	
	public String getNovaResposta1() {
		return novaResposta1;
	}

	public String getNovaResposta2() {
		return novaResposta2;
	}
	
	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}
	
	public void setNovaResposta1(String novaResposta1) {
		this.novaResposta1 = novaResposta1;
	}

	public void setNovaResposta2(String novaResposta2) {
		this.novaResposta2 = novaResposta2;
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
	
	public void cancelar() {
		MenuAction.setId_Menu(0);
	}
	
}