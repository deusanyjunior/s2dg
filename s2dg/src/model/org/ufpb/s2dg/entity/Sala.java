package org.ufpb.s2dg.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sala")
public class Sala implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private Set<TurmaSala> turmaSalas = new HashSet<TurmaSala>(0);
	private String sala;
	
	public Sala() {}
	
	public Sala(long id, Set<TurmaSala> turmaSalas, String sala) {
		this.id = id;
		this.turmaSalas = turmaSalas;
		this.sala = sala;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sala")
	public Set<TurmaSala> getTurmaSalas() {
		return turmaSalas;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public void setTurmaSalas(Set<TurmaSala> turmaSalas) {
		this.turmaSalas = turmaSalas;
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
