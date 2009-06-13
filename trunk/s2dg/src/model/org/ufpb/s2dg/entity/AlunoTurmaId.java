package org.ufpb.s2dg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Embeddable
public class AlunoTurmaId implements Serializable {

	private static final long serialVersionUID = 1L;
	private String matriculaAluno;
	private long idTurma;

	public AlunoTurmaId() {
	}

	public AlunoTurmaId(String matriculaAluno, long idTurma) {
		this.matriculaAluno = matriculaAluno;
		this.idTurma = idTurma;
	}

	@Column(name = "matricula_aluno", 
			nullable = false, 
			length = 9)
	@NotNull
	@Length(max = 9)
	public String getMatriculaAluno() {
		return this.matriculaAluno;
	}

	public void setMatriculaAluno(String matriculaAluno) {
		this.matriculaAluno = matriculaAluno;
	}

	@Column(name = "id_turma", nullable = false)
	@NotNull
	public long getIdTurma() {
		return this.idTurma;
	}

	public void setIdTurma(long idTurma) {
		this.idTurma = idTurma;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AlunoTurmaId))
			return false;
		AlunoTurmaId castOther = (AlunoTurmaId) other;

		return ((this.getMatriculaAluno() == castOther.getMatriculaAluno()) || (this
				.getMatriculaAluno() != null
				&& castOther.getMatriculaAluno() != null && this
				.getMatriculaAluno().equals(castOther.getMatriculaAluno())))
				&& (this.getIdTurma() == castOther.getIdTurma());
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getMatriculaAluno() == null ? 0 : this.getMatriculaAluno()
						.hashCode());
		result = 37 * result + (int) this.getIdTurma();
		return result;
	}

}
