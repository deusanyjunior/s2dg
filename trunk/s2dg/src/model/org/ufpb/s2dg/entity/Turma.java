package org.ufpb.s2dg.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
	private Set<Professor> professores = new HashSet<Professor>(0);
	private Periodo periodo;
	private Disciplina disciplina;
	private String numero;
	private Set<AlunoTurma> alunoTurmas = new HashSet<AlunoTurma>(0);
	private Set<Avaliacao> avaliacoes = new HashSet<Avaliacao>(0);
	private boolean calcularMediaAutomaticamente = false;
	private String planoDeCurso;
	
	public Turma() {
	}

	public Turma(long id, Disciplina disciplina, String numero) {
		this.id = id;
		this.disciplina = disciplina;
		this.numero = numero;
	}

	public Turma(long id, Set<Professor> professores, Periodo periodo,
			Disciplina disciplina, String numero, Set<AlunoTurma> alunoTurmas,
			Set<Avaliacao> avaliacoes, boolean calcularMediaAutomaticamente, String planoDeCurso) {
		this.id = id;
		this.professores = professores;
		this.periodo = periodo;
		this.disciplina = disciplina;
		this.numero = numero;
		this.alunoTurmas = alunoTurmas;
		this.avaliacoes = avaliacoes;
		this.calcularMediaAutomaticamente = calcularMediaAutomaticamente;
		this.planoDeCurso = planoDeCurso;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "professor_turma", schema = "public", joinColumns = { @JoinColumn(name = "turma_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "matricula_docente", nullable = false, updatable = false) })
	public Set<Professor> getProfessores() {
		return this.professores;
	}

	public void setProfessores(Set<Professor> professores) {
		this.professores = professores;
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
	public Set<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(Set<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	@Column(name = "calcula_media_auto")
	public boolean isCalcularMediaAutomaticamente() {
		return calcularMediaAutomaticamente;
	}

	public void setCalcularMediaAutomaticamente(boolean calcularMediaAutomaticamente) {
		this.calcularMediaAutomaticamente = calcularMediaAutomaticamente;
	}
	
	@Column(name = "plano_de_curso", nullable = true)
	@Lob
	public String getPlanoDeCurso() {
		return this.planoDeCurso;
	}

	public void setPlanoDeCurso(String planoDeCurso) {
		this.planoDeCurso = planoDeCurso;
	}
	
}