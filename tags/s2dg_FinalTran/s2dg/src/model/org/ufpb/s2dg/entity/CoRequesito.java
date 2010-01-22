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
@Table(name = "co_requesito")
public class CoRequesito {

	private long id;
	private Curriculo curriculo;	
	private Disciplina disciplina1;	
	private Disciplina disciplina2;
	

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
	@JoinColumn(name = "disciplina1", nullable = false)
	@NotNull
	public Disciplina getDisciplina1() {
		return disciplina1;
	}
	
	public void setDisciplina1(Disciplina disciplina1) {
		this.disciplina1 = disciplina1;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disciplina2", nullable = false)
	@NotNull
	public Disciplina getDisciplina2() {
		return disciplina2;
	}
	
	public void setDisciplina2(Disciplina disciplina2) {
		this.disciplina2 = disciplina2;
	}
	
	
	
	
}

