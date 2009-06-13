package org.ufpb.s2dg.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "aluno")
public class Aluno implements Serializable {

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
	@Column(name = "matricula", 
			unique = true, 
			nullable = false, 
			length = 9)
	@NotNull
	@Length(max = 9)
	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

}
