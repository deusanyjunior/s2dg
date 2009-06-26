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
@Table(name = "aluno_turma_nota")
public class AlunoTurmaNota implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private AlunoTurma alunoTurma;
	private Nota nota;
	private float valorDaNota;
	
	public AlunoTurmaNota() {
	}

	public AlunoTurmaNota(long id, AlunoTurma alunoTurma, Nota nota,
			float valorDaNota) {
		this.id = id;
		this.alunoTurma = alunoTurma;
		this.nota = nota;
		this.valorDaNota = valorDaNota;
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
	@JoinColumn(name = "id_aluno_turma", nullable = false, insertable = false, updatable = false)
	@NotNull
	public AlunoTurma getAlunoTurma() {
		return alunoTurma;
	}

	public void setAlunoTurma(AlunoTurma alunoTurma) {
		this.alunoTurma = alunoTurma;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nota", nullable = false, insertable = false, updatable = false)
	@NotNull
	public Nota getNota() {
		return nota;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}

	@Column(name = "nota", precision = 8, scale = 8)
	public float getValorDaNota() {
		return valorDaNota;
	}

	public void setValorDaNota(float valorDaNota) {
		this.valorDaNota = valorDaNota;
	}
		
}
