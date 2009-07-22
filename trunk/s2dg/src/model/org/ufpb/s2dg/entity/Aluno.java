package org.ufpb.s2dg.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "aluno")
public class Aluno implements Serializable {

	private static final long serialVersionUID = 1L;
	private String matricula;
	private Set<AlunoTurma> alunoTurmas = new HashSet<AlunoTurma>(0);
	
	@OneToOne(mappedBy="aluno")
	private Usuario usuario;

	public Aluno() {
	}

	public Aluno(String matricula) {
		this.matricula = matricula;
	}

	public Aluno(String matricula, Set<Usuario> usuarios,
			Set<AlunoTurma> alunoTurmas) {
		this.matricula = matricula;
		this.alunoTurmas = alunoTurmas;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "aluno")
	public Set<AlunoTurma> getAlunoTurmas() {
		return this.alunoTurmas;
	}

	public void setAlunoTurmas(Set<AlunoTurma> alunoTurmas) {
		this.alunoTurmas = alunoTurmas;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
