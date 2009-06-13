package org.ufpb.s2dg.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

@Entity
@Table(name = "periodo")
public class Periodo implements Serializable {

	private static final long serialVersionUID = 1L;
	private PeriodoId id;
	private Set<Turma> turmas = new HashSet<Turma>(0);

	public Periodo() {
	}

	public Periodo(PeriodoId id) {
		this.id = id;
	}

	public Periodo(PeriodoId id, Set<Turma> turmas) {
		this.id = id;
		this.turmas = turmas;
	}

	@EmbeddedId
	@AttributeOverrides( {
		@AttributeOverride(name = "ano", 
						   column = @Column(name = "ano", nullable = false, length = 4)),
		@AttributeOverride(name = "semestre", 
						   column = @Column(name = "semestre", nullable = false, length = 1)) 
	})
	@NotNull
	public PeriodoId getId() {
		return this.id;
	}

	public void setId(PeriodoId id) {
		this.id = id;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "periodo")
	public Set<Turma> getTurmas() {
		return this.turmas;
	}

	public void setTurmas(Set<Turma> turmas) {
		this.turmas = turmas;
	}

}
