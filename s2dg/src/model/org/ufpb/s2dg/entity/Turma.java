package org.ufpb.s2dg.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "turma")
public class Turma implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private Professor professor;
	private Periodo periodo;
	private Disciplina disciplina;
	private String numero;
	private Set<AlunoTurma> alunoTurmas = new HashSet<AlunoTurma>(0);
	private Set<Nota> notas = new HashSet<Nota>(0);
	private boolean calcularMediaAutomaticamente = false;

	public Turma() {
	}

	public Turma(long id, Disciplina disciplina, String numero) {
		this.id = id;
		this.disciplina = disciplina;
		this.numero = numero;
	}

	public Turma(long id, Professor professor, Periodo periodo,
			Disciplina disciplina, String numero, Set<AlunoTurma> alunoTurmas, Set<Nota> notas, boolean calcularMediaAutomaticamente) {
		this.id = id;
		this.professor = professor;
		this.periodo = periodo;
		this.disciplina = disciplina;
		this.numero = numero;
		this.alunoTurmas = alunoTurmas;
		this.notas = notas;
		this.calcularMediaAutomaticamente = calcularMediaAutomaticamente;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_professor")
	public Professor getProfessor() {
		return this.professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_periodo")
	public Periodo getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_disciplina", nullable = false)
	@NotNull
	public Disciplina getDisciplina() {
		return this.disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	@Column(name = "numero", nullable = false, length = 2)
	@NotNull
	@Length(max = 2)
	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "turma")
	public Set<AlunoTurma> getAlunoTurmas() {
		return this.alunoTurmas;
	}

	public void setAlunoTurmas(Set<AlunoTurma> alunoTurmas) {
		this.alunoTurmas = alunoTurmas;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "turma")
	public Set<Nota> getNotas() {
		return notas;
	}

	public void setNotas(Set<Nota> notas) {
		this.notas = notas;
	}

	@Column(name = "calcula_media_auto")
	public boolean isCalcularMediaAutomaticamente() {
		return calcularMediaAutomaticamente;
	}

	public void setCalcularMediaAutomaticamente(boolean calcularMediaAutomaticamente) {
		this.calcularMediaAutomaticamente = calcularMediaAutomaticamente;
	}
	
}
