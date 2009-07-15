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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

@Entity
@Table(name = "calendario")
public class Calendario implements Serializable {
	private static final long serialVersionUID = 1L;
	private Set<DataEvento> datasEventos = new HashSet<DataEvento>(0);
	private Periodo periodo;
	private long id;
	
	public Calendario () {}
	
	public Calendario(long id, Periodo periodo) {
		this.id = id;
		this.periodo = periodo;
	}

	public Calendario(long id, Periodo periodo, Set<DataEvento> eventsDates) {
		this.id = id;
		datasEventos = eventsDates;
		this.periodo = periodo;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@NotNull
	@OneToOne(fetch = FetchType.LAZY, cascade= {CascadeType.ALL})
	@JoinColumn(name = "id_periodo")
	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "calendario")
	public Set<DataEvento> getDatasEventos() {
		return datasEventos;
	}

	public void setDatasEventos(Set<DataEvento> datasEventos) {
		this.datasEventos = datasEventos;
	}
}

/*inicio do periodo, inicio das matriculas, fim das matriculas, 
 * ultimo dia de trancamento, termino periodo letivo, ultimo dia implantacao
 * de notas
 * @temporal*/

 