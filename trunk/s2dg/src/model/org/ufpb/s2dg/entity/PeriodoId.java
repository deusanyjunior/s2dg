package org.ufpb.s2dg.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Embeddable
public class PeriodoId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String ano;
	private char semestre;

	public PeriodoId() {
	}

	public PeriodoId(String ano, char semestre) {
		this.ano = ano;
		this.semestre = semestre;
	}

	@Column(name = "ano", nullable = false, length = 4)
	@NotNull
	@Length(max = 4)
	public String getAno() {
		return this.ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	@Column(name = "semestre", nullable = false, length = 1)
	@NotNull
	public char getSemestre() {
		return this.semestre;
	}

	public void setSemestre(char semestre) {
		this.semestre = semestre;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PeriodoId))
			return false;
		PeriodoId castOther = (PeriodoId) other;

		return ((this.getAno() == castOther.getAno()) || (this.getAno() != null
				&& castOther.getAno() != null && this.getAno().equals(
				castOther.getAno())))
				&& (this.getSemestre() == castOther.getSemestre());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getAno() == null ? 0 : this.getAno().hashCode());
		result = 37 * result + this.getSemestre();
		return result;
	}

}
