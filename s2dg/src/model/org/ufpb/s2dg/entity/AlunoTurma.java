package org.ufpb.s2dg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

@Entity
@Table(name = "aluno_turma")
public class AlunoTurma implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private Turma turma;
	private Aluno aluno;
	private Float media;
	private Integer faltas;

	public AlunoTurma() {
	}

	public AlunoTurma(long id, Turma turma, Aluno aluno) {
		this.id = id;
		this.turma = turma;
		this.aluno = aluno;
	}

	public AlunoTurma(long id, Turma turma, Aluno aluno, Float media,
			Integer frequencia) {
		this.id = id;
		this.turma = turma;
		this.aluno = aluno;
		this.media = media;
		this.faltas = frequencia;
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
	@JoinColumn(name = "id_turma", nullable = false)
	@NotNull
	public Turma getTurma() {
		return this.turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "matricula_aluno", nullable = false)
	@NotNull
	public Aluno getAluno() {
		return this.aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	@Column(name = "media", precision = 8, scale = 8)
	public Float getMedia() {
		return this.media;
	}

	public void setMedia(Float media) {
		this.media = media;
	}

	@Column(name = "faltas")
	public Integer getFaltas() {
		return this.faltas;
	}

	public void setFaltas(Integer faltas) {
		this.faltas = faltas;
	}

}
