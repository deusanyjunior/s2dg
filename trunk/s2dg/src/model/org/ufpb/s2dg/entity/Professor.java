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
@Table(name = "professor")
public class Professor implements Serializable {

	private static final long serialVersionUID = 1L;
	private String matricula;

	public Professor() {
	}

	public Professor(String matricula) {
		this.matricula = matricula;
	}

	public Professor(String matricula, Set<Usuario> usuarios) {
		this.matricula = matricula;
	}

	@Id
	@Column(name = "matricula", unique = true, nullable = false, length = 15)
	@NotNull
	@Length(max = 15)
	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

}
