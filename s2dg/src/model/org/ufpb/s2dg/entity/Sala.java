package org.ufpb.s2dg.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sala")
public class Sala implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private Set<Turma> turmas = new HashSet<Turma>(0);
	private String sala;
	
	public Sala() {}
	
	public Sala(long id, Set<Turma> turmas, String sala) {
		this.id = id;
		this.turmas = turmas;
		this.sala = sala;
	}
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "turma_sala", schema = "public", joinColumns = { @JoinColumn(name = "id_sala", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "id_turma", nullable = false, updatable = false) })
	public Set<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(Set<Turma> turmas) {
		this.turmas = turmas;
	}
	
	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
}
