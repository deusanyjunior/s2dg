package org.ufpb.s2dg.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.NotNull;

@Entity
@Table(name="Log")
public class Log implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(unique=true, name="id", nullable=false)
	private long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	@Lob
	private String mensagem;
	
	@Column(nullable=false, name="cpf_usuario", length=11)
	@NotNull
	private String cpfUsuario;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getCpfUsuario() {
		return cpfUsuario;
	}

	public void setCpfUsuario(String cpf) {
		this.cpfUsuario = cpf;
	}
	
}
