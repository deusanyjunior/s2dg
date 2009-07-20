package org.ufpb.s2dg.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "disciplina")
public class Disciplina implements Serializable {

	private static final long serialVersionUID = 1L;
	private String codigo;
	private String nome;
	private int creditos;
	private Set<Turma> turmas = new HashSet<Turma>(0);
	private Set<Curriculo> curriculos;
	private Set<Disciplina> pre_requisitos;
	private Set<Disciplina> co_requisitos;

	public Disciplina() {
	}

	public Disciplina(String codigo, String nome, int creditos) {
		this.codigo = codigo;
		this.nome = nome;
		this.creditos = creditos;
	}

	public Disciplina(String codigo, String nome, int creditos,
			Set<Turma> turmas, Set<Curriculo> curriculos, Set<Disciplina> pre_requisitos, Set<Disciplina> co_requisitos) {
		this.codigo = codigo;
		this.nome = nome;
		this.creditos = creditos;
		this.turmas = turmas;
		this.curriculos = curriculos;
		this.pre_requisitos = pre_requisitos;
		this.co_requisitos = co_requisitos;
	}

	@Id
	@Column(name = "codigo", unique = true, nullable = false, length = 7)
	@NotNull
	@Length(max = 7)
	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Column(name = "nome", nullable = false, length = 100)
	@NotNull
	@Length(max = 100)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "creditos", nullable = false)
	public int getCreditos() {
		return this.creditos;
	}

	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "disciplina")
	public Set<Turma> getTurmas() {
		return this.turmas;
	}

	public void setTurmas(Set<Turma> turmas) {
		this.turmas = turmas;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "curriculo_disciplina", schema = "public", joinColumns = { @JoinColumn(name = "codigo_disciplina", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "id_curriculo", nullable = false, updatable = false) })
	public Set<Curriculo> getCurriculos() {
		return curriculos;
	}

	public void setCurriculos(Set<Curriculo> curriculos) {
		this.curriculos = curriculos;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "pre_requisito", schema = "public", joinColumns = { @JoinColumn(name = "codigo_disciplina1", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "codigo_disciplina2", nullable = false, updatable = false) })
	public Set<Disciplina> getPre_requisitos() {
		return pre_requisitos;
	}

	public void setPre_requisitos(Set<Disciplina> pre_requisitos) {
		this.pre_requisitos = pre_requisitos;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "co_requisito", schema = "public", joinColumns = { @JoinColumn(name = "codigo_disciplina1", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "codigo_disciplina2", nullable = false, updatable = false) })
	public Set<Disciplina> getCo_requisitos() {
		return co_requisitos;
	}

	public void setCo_requisitos(Set<Disciplina> co_requisitos) {
		this.co_requisitos = co_requisitos;
	}

}
