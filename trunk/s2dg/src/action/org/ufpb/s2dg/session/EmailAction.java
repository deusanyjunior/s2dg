package org.ufpb.s2dg.session;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.faces.Renderer;
import org.jboss.seam.log.Log;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.net.HttpURLConnection;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Name("emailAction")
public class EmailAction {
	
	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final String SMTP_AUTH_USER = "es.s2dg";
    private static final String SMTP_AUTH_PWD  = "engenharia";
    private static final String SMTP_PORT  = "465";

    private static final String EMAIL = "es.s2dg@gmail.com"; 
    private static final String TITULO = "Recuperação";
    private String toEmail;
    private String MENSAGEM = "";
    
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
    
    public void enviar(){
    	setEmail();
    	setSenha();
    	
        try 
        {
            if(isConnected())
                sendMail(getToEmail(), EMAIL, TITULO, getMENSAGEM());
            else
                System.err.println("Connection-fault");
        } 
        catch (HeadlessException e) 
        {
            System.err.println(e.getMessage());
        } 
        catch (IOException e) 
        {
            System.err.println(e.getMessage());
        }
    }
    
    private void sendMail(String to, String from, String titulo, String message)
    {
        try
        {
            Properties props = new Properties();
            
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.port", SMTP_PORT);
            props.put("mail.smtp.starttls.enable","true");
            props.put("mail.smtp.host", SMTP_HOST_NAME);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.socketFactory.port", SMTP_PORT);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");
                        
            Authenticator auth = new SMTPAuthenticator();
            
            Session session = Session.getInstance(props, auth);
            session.setDebug(false);
            
            Message msg = new MimeMessage(session);
            
            InternetAddress addressFrom = new InternetAddress(from);
            msg.setFrom(addressFrom);
            
            InternetAddress []addressTo = new InternetAddress[0];
            
            addressTo[0] = new InternetAddress(to);
	
            msg.setRecipients(Message.RecipientType.TO, addressTo);
            
            msg.setText(message);
            msg.setSubject(titulo);
            msg.setContent(message, "text/plain");
	
            Transport.send(msg);
        }
        catch(MessagingException e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    private boolean isConnected() throws IOException 
    {         
        HttpURLConnection httpConn = null;
        try 
        {
            URL mandarMail = new URL("http://www.google.com");  
            URLConnection conn = mandarMail.openConnection(); 
            httpConn = (java.net.HttpURLConnection) conn;  
            httpConn.connect();  
            if(httpConn.getResponseCode() == 200)  
                return true;  
            return false;
        } 
        catch (MalformedURLException e) 
        {
            System.err.println(e.getMessage());
        }  
        return false;
    }
    
    private class SMTPAuthenticator extends Authenticator
    {
        public PasswordAuthentication getPasswordAuthentication()
        {
            String username = SMTP_AUTH_USER;
            String password = SMTP_AUTH_PWD;
            
            return new PasswordAuthentication(username, password);
        }
    }
    
    /**
     * To control if the click was done
     * @return
     */		
    public void botaoPressionado(){
		setClicou(true);
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
		
		String msg = "A sua senha é: " + getSenha();
		
		setMENSAGEM(msg);
	}

	public void setEmail(){
		setToEmail(fachada.getEmail(getCPF()));
	}
	
	public void setSenha(){
		setSenha(fachada.getSenha(CPF));
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setMENSAGEM(String mENSAGEM) {
		MENSAGEM = mENSAGEM;
	}

	public String getMENSAGEM() {
		return MENSAGEM;
	}
}
