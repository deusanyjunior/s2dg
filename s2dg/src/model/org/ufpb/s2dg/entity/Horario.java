package org.ufpb.s2dg.entity;

import java.io.Serializable;
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
@Table(name = "horario")
public class Horario implements Serializable {

	public enum Dia {SEG, TER, QUA, QUI, SEX, SAB, DOM}
	
	private static final long serialVersionUID = 1L;
	private long id;
	private Dia dia;
	private int horaInicio;
	private int horaFim;
	private int minutoInicio;
	private int minutoFim;
	private Set<Turma> turmas;

	public Horario() {
	}

	public Horario(long id) {
		this.id = id;
	}
	
	public Horario(long id, Dia dia, int horaInicio, int horaFim,
			int minutoInicio, int minutoFim, Set<Turma> turmas) {
		this.id = id;
		this.dia = dia;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		this.turmas = turmas;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "dia")
	public Dia getDia() {
		return dia;
	}

	public void setDia(Dia dia) {
		this.dia = dia;
	}

	@Column(name = "hora_inicio")
	public int getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(int horaInicio) {
		this.horaInicio = horaInicio;
	}

	@Column(name = "hora_fim")
	public int getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(int horaFim) {
		this.horaFim = horaFim;
	}

	@Column(name = "minuto_inicio")
	public int getMinutoInicio() {
		return minutoInicio;
	}

	public void setMinutoInicio(int minutoInicio) {
		this.minutoInicio = minutoInicio;
	}

	@Column(name = "minuto_fim")
	public int getMinutoFim() {
		return minutoFim;
	}

	public void setMinutoFim(int minutoFim) {
		this.minutoFim = minutoFim;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "turma_horario", schema = "public", joinColumns = { @JoinColumn(name = "id_horario", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "id_turma", nullable = false, updatable = false) })
	public Set<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(Set<Turma> turmas) {
		this.turmas = turmas;
	}

	public boolean temChoque(Horario outroHorario) {
		if(this.dia == outroHorario.getDia()) {
			if(this.horaFim > outroHorario.getHoraInicio()) {
				if(this.horaInicio < outroHorario.getHoraFim())
					return true;
			}
		}
		return false;
	}
	
	public String toString() {
		String dia = this.dia.name();
		String h1 = Integer.toString(this.horaInicio);
		if(h1.length() == 1)
			h1 = '0' + h1;
		String m1 = Integer.toString(this.minutoInicio);
		if(m1.length() == 1)
			m1 = '0' + m1;
		String h2 = Integer.toString(this.horaFim);
		if(h2.length() == 1)
			h2 = '0' + h2;
		String m2 = Integer.toString(this.minutoFim);
		if(m2.length() == 1)
			m2 = '0' + m2;
		return dia+", "+h1+":"+m1+" - "+h2+":"+m2;
	}
	
}
