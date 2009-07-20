package org.ufpb.s2dg.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "oferta")
public class Oferta implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private Curso curso;
	private Turma turma;
	private int numeroDeVagasOfertadas;
	private int numeroDeVagasDisponiveis;

	public Oferta() {
	}

	public Oferta(long id) {
		this.id = id;
	}
	
	public Oferta(long id, Curso curso, Turma turma,
			int numeroDeVagasOfertadas, int numeroDeVagasDisponiveis) {
		this.id = id;
		this.curso = curso;
		this.turma = turma;
		this.numeroDeVagasOfertadas = numeroDeVagasOfertadas;
		this.numeroDeVagasDisponiveis = numeroDeVagasDisponiveis;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade= {CascadeType.ALL})
	@JoinColumn(name = "id_curso")
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade= {CascadeType.ALL})
	@JoinColumn(name = "id_turma")
	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	@Column(name = "vagas_ofertadas", updatable = false)
	public int getNumeroDeVagasOfertadas() {
		return numeroDeVagasOfertadas;
	}

	public void setNumeroDeVagasOfertadas(int numeroDeVagasOfertadas) {
		this.numeroDeVagasOfertadas = numeroDeVagasOfertadas;
	}

	@Column(name = "vagas_disponiveis")
	public int getNumeroDeVagasDisponiveis() {
		return numeroDeVagasDisponiveis;
	}

	public void setNumeroDeVagasDisponiveis(int numeroDeVagasDisponiveis) {
		this.numeroDeVagasDisponiveis = numeroDeVagasDisponiveis;
	}
	
	public void decrementaVaga() {
		this.numeroDeVagasDisponiveis--;
	}
	
}
