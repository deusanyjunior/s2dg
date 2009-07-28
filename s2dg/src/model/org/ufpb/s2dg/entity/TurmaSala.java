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
@Table(name = "turma_sala")
public class TurmaSala implements Serializable {
	private static final long serialVersionUID = 1L;
	private Turma turma;
	private Sala sala;
	private long id;
	
	public TurmaSala() {}
	
	public TurmaSala(long id, Turma turma, Sala sala) {
		this.id = id;
		this.turma = turma;
		this.sala = sala;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_turma", nullable = false)
	@NotNull
	public Turma getTurma() {
		return turma;
	}
	
	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_sala", nullable = false)
	@NotNull
	public Sala getSala() {
		return sala;
	}
	
	public void setSala(Sala sala) {
		this.sala = sala;
	}
}	
