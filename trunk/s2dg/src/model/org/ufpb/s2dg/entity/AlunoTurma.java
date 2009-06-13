package org.ufpb.s2dg.entity;


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "aluno_turma", schema = "public")
public class AlunoTurma implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private AlunoTurmaId id;
	private Turma turma;
	private Aluno aluno;
	private Float media;
	private Integer frequencia;

	public AlunoTurma() {
	}

	public AlunoTurma(AlunoTurmaId id, Turma turma, Aluno aluno) {
		this.id = id;
		this.turma = turma;
		this.aluno = aluno;
	}

	public AlunoTurma(AlunoTurmaId id, Turma turma, Aluno aluno, Float media,
			Integer frequencia) {
		this.id = id;
		this.turma = turma;
		this.aluno = aluno;
		this.media = media;
		this.frequencia = frequencia;
	}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "matriculaAluno", column = @Column(name = "matricula_aluno", nullable = false, length = 9)),
			@AttributeOverride(name = "idTurma", column = @Column(name = "id_turma", nullable = false)) })
	@NotNull
	public AlunoTurmaId getId() {
		return this.id;
	}

	public void setId(AlunoTurmaId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_turma", nullable = false, insertable = false, updatable = false)
	@NotNull
	public Turma getTurma() {
		return this.turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "matricula_aluno", nullable = false, insertable = false, updatable = false)
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

	@Column(name = "frequencia")
	public Integer getFrequencia() {
		return this.frequencia;
	}

	public void setFrequencia(Integer frequencia) {
		this.frequencia = frequencia;
	}

}
