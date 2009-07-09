package org.ufpb.s2dg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

@Entity
@Table(name = "aluno_turma_avaliacao")
public class AlunoTurmaAvaliacao implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private AlunoTurma alunoTurma;
	private Avaliacao avaliacao;
	private float nota;
	
	public AlunoTurmaAvaliacao() {
	}

	public AlunoTurmaAvaliacao(long id, AlunoTurma alunoTurma, Avaliacao avaliacao,
			float nota) {
		this.id = id;
		this.alunoTurma = alunoTurma;
		this.avaliacao = avaliacao;
		this.nota = nota;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_aluno_turma", nullable = false, updatable = false)
	@NotNull
	public AlunoTurma getAlunoTurma() {
		return alunoTurma;
	}

	public void setAlunoTurma(AlunoTurma alunoTurma) {
		this.alunoTurma = alunoTurma;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_avaliacao", nullable = false, updatable = false)
	@NotNull
	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	@Column(name = "nota", precision = 8, scale = 8)
	public float getNota() {
		return nota;
	}

	public void setNota(float nota) {
		this.nota = nota;
	}
		
}
