package org.ufpb.s2dg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "turma", schema = "public")
public class Turma implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private Periodo periodo;
	private Disciplina disciplina;
	private String numero;

	public Turma() {
	}

	public Turma(long id, Periodo periodo, Disciplina disciplina,
			String numero) {
		this.id = id;
		this.periodo = periodo;
		this.disciplina = disciplina;
		this.numero = numero;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, precision = 131089, scale = 0)
	@NotNull
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns( {
			@JoinColumn(name = "id_periodo_ano", referencedColumnName = "ano", nullable = false),
			@JoinColumn(name = "id_periodo_semestre", referencedColumnName = "semestre", nullable = false) })
	@NotNull
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

}
