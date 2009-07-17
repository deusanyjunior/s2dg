package org.ufpb.s2dg.session;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.faces.Renderer;
import org.jboss.seam.log.Log;

@Name("emailAction")
public class EmailAction {

    private String toEmail;
    private String message;
    private String subject;
    
    private String fromName;
    private String fromEmail;

    private boolean clicou = false;
    private String CPF;
    private String senha;
    
	@In
    Renderer renderer;
    @In
    FacesMessages facesMessages;
    @Logger
    Log log;
    @In
    Fachada fachada;

    public EmailAction(){
    	setClicou(false);
    }
    
    public void sendEmail(){

        try{
            renderer.render("/email.xhtml");
            facesMessages.add("Email enviado com sucesso.");
        }
        catch(Exception e){
            log.error("Erro ao tentar enviar o email.", e);
            facesMessages.add("Incapaz de enviar o email no momento. Teste mais tarde");
        }

    }
    
    /**
     * To control if the click was done
     * @return
     */		
    public void botaoPressionado(){
		setClicou(true);
	} 

    /**
     * @return the toEmail
     */
    public String getToEmail() {
        return toEmail;
    }

    /**
     * @param toEmail the toEmail to set
     */
    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the fromName
     */
    public String getFromName() {
        return fromName;
    }

    /**
     * @param fromName the fromName to set
     */
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    /**
     * @return the fromEmail
     */
    public String getFromEmail() {
        return fromEmail;
    }

    /**
     * @param fromEmail the fromEmail to set
     */
    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

	public void setClicou(boolean clicou) {
		this.clicou = clicou;
	}

	public boolean isClicou() {
		return clicou;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getCPF() {
		return CPF;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setEmail(){
		setToEmail(fachada.getEmail(getCPF()));
	}
	
	public void setSenha(){
		setSenha(fachada.getSenha(CPF));
	}
}
