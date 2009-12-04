package org.ufpb.s2dg.entity;

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
@Table(name = "pre_requesito")
public class PreRequesito {

	private long id;
	private Curriculo curriculo;	
	private Disciplina disciplina;	
	private Disciplina dependencia;
	

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "curriculo", nullable = false)
	@NotNull
	public Curriculo getCurriculo() {
		return curriculo;
	}
	
	public void setCurriculo(Curriculo curriculo) {
		this.curriculo = curriculo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disciplina", nullable = false)
	@NotNull
	public Disciplina getDisciplina() {
		return disciplina;
	}
	
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dependencia", nullable = false)
	@NotNull
	public Disciplina getDependencia() {
		return dependencia;
	}
	
	public void setDependencia(Disciplina dependencia) {
		this.dependencia = dependencia;
	}
	
	
	
	
}
