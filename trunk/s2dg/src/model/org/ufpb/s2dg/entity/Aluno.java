package org.ufpb.s2dg.entity;

// Generated 13/06/2009 11:07:54 by Hibernate Tools 3.2.2.GA

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "aluno", schema = "public")
public class Aluno implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String matricula;

	public Aluno() {
	}

	public Aluno(String matricula) {
		this.matricula = matricula;
	}

	public Aluno(String matricula, Set<Usuario> usuarios) {
		this.matricula = matricula;
	}

	@Id
	@Column(name = "matricula", unique = true, nullable = false, length = 9)
	@NotNull
	@Length(max = 9)
	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

}
