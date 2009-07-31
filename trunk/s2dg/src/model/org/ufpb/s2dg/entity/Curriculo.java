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
	private String numero;
	private int minimoCreditosCurriculo;
	private int minimoDisciplinas;
	private int maximoTrancamentosParciais;
	private int minimoSemestres;
	private int maximoSemestres;
	private int trancamentosTotais;
	private int matriculaInstitucional;

	public Curriculo() {
	}

	public Curriculo(long id) {
		this.id = id;
	}
	
	public Curriculo(long id, Set<Aluno> alunos, Curso curso, Set<Disciplina> disciplinas,
			int minimoCreditos, int maximoCreditos, String numero, int minimoCreditosCurriculo,
			int minimoDisciplinas, int maximoTrancamentosParciais, int minimoSemestres, int maximoSemestres,
			int trancamentosTotais, int matriculaInstitucional) {
		this.id = id;
		this.alunos = alunos;
		this.curso = curso;
		this.disciplinas = disciplinas;
		this.minimoCreditos = minimoCreditos;
		this.maximoCreditos = maximoCreditos;
		this.numero = numero;
		this.minimoCreditosCurriculo = minimoCreditosCurriculo;
		this.minimoDisciplinas = minimoDisciplinas;
		this.maximoTrancamentosParciais = maximoTrancamentosParciais;
		this.minimoSemestres = minimoSemestres;
		this.maximoSemestres = maximoSemestres;
		this.trancamentosTotais = trancamentosTotais;
		this.matriculaInstitucional = matriculaInstitucional;
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

	@Column(name = "numero")
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Column(name = "min_creditos_curriculo")
	public int getMinimoCreditosCurriculo() {
		return minimoCreditosCurriculo;
	}

	public void setMinimoCreditosCurriculo(int minimoCreditosCurriculo) {
		this.minimoCreditosCurriculo = minimoCreditosCurriculo;
	}

	@Column(name = "min_disciplinas")
	public int getMinimoDisciplinas() {
		return minimoDisciplinas;
	}

	public void setMinimoDisciplinas(int minimoDisciplinas) {
		this.minimoDisciplinas = minimoDisciplinas;
	}

	@Column(name = "max_trancamento_parcial")
	public int getMaximoTrancamentosParciais() {
		return maximoTrancamentosParciais;
	}

	public void setMaximoTrancamentosParciais(int maximoTrancamentosParciais) {
		this.maximoTrancamentosParciais = maximoTrancamentosParciais;
	}

	@Column(name = "min_semestres")
	public int getMinimoSemestres() {
		return minimoSemestres;
	}

	public void setMinimoSemestres(int minimoSemestres) {
		this.minimoSemestres = minimoSemestres;
	}

	@Column(name = "max_semestres")
	public int getMaximoSemestres() {
		return maximoSemestres;
	}

	public void setMaximoSemestres(int maximoSemestres) {
		this.maximoSemestres = maximoSemestres;
	}

	@Column(name = "trancamentos_totais")
	public int getTrancamentosTotais() {
		return trancamentosTotais;
	}

	public void setTrancamentosTotais(int trancamentosTotais) {
		this.trancamentosTotais = trancamentosTotais;
	}

	@Column(name = "matricula_institucional")
	public int getMatriculaInstitucional() {
		return matriculaInstitucional;
	}

	public void setMatriculaInstitucional(int matriculaInstitucional) {
		this.matriculaInstitucional = matriculaInstitucional;
	}

}
