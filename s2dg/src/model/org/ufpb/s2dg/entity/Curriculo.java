package org.ufpb.s2dg.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "curriculo")
public class Curriculo implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private Set<Aluno> alunos;
	private Curso curso;
	private Set<Disciplina> disciplinas;
	private int minimoCreditos;
	private int maximoCreditos;

	public Curriculo() {
	}

	public Curriculo(long id) {
		this.id = id;
	}
	
	public Curriculo(long id, Set<Aluno> alunos, Curso curso, Set<Disciplina> disciplinas,
			int minimoCreditos, int maximoCreditos) {
		this.id = id;
		this.alunos = alunos;
		this.curso = curso;
		this.disciplinas = disciplinas;
		this.minimoCreditos = minimoCreditos;
		this.maximoCreditos = maximoCreditos;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@OneToMany(mappedBy="curriculo")
	public Set<Aluno> getAlunos() {
		return alunos;
	}
	
	public void setAlunos(Set<Aluno> alunos) {
		this.alunos = alunos;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade= {CascadeType.ALL})
	@JoinColumn(name = "id_curso")
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "curriculo_disciplina", schema = "public", joinColumns = { @JoinColumn(name = "id_curriculo", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "codigo_disciplina", nullable = false, updatable = false) })
	public Set<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(Set<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	@Column(name = "min_creditos")
	public int getMinimoCreditos() {
		return minimoCreditos;
	}

	public void setMinimoCreditos(int minimoCreditos) {
		this.minimoCreditos = minimoCreditos;
	}

	@Column(name = "max_creditos")
	public int getMaximoCreditos() {
		return maximoCreditos;
	}

	public void setMaximoCreditos(int maximoCreditos) {
		this.maximoCreditos = maximoCreditos;
	}

}
