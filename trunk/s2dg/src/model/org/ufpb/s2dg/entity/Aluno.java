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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "aluno")
public class Aluno implements Serializable {

	public enum SituacaoAcademica {INDEFINIDO, REGULAR, GRADUADO};
	public enum FormaIngresso {VESTIBULAR, DECISAO_CONSEPE,DECISAO_JUDICIAL,MOBILIDADE_INTERNA, VESTIBULAR_EAD_DEMANDA_SOCIAL,
	VESTIBULAR_EAD_PROFESSOR,VESTIBULAR_PROESP_UFPB,VESTIBULAR_PROESP_OUTROS_ORG_PUB,REOPÇÃO_DE_POLO_EAD, EAD_TRANSFERENCIA_PSTV,
	DISCIPLINAS_ISOLADAS_ESPECIAL,TRANSFERENCIA_PSTV, CONVENIO, MUDANCA_DE_CAMPUS_CURSO, GRADUADO, VESTIBULAR_MUDANCA_DE_CURSO,
	TRANSFERENCIA_MUDANCA_DE_CURSO, CONVENIO_MUDANCA_DE_CURSO, TRANSFERENCIA_EX_OFICIO,TRANSFERENCIA_POR_FORCA_LIMINAR,
	TRANSFERENCIA_POR_FORCA_SENTENCA,PEC,REINGRESSO,PROGRAMA_PIANI,VESTIBULAR_2,PROGRAMA_DE_MOBILIDADE_ESTUDANTIL,PEC_MSC,REINGRESSO_POR_DECISAO_DO_CONSEPE,
	REOPÇÃO_MUDANCA_DE_TURNO
	}
	
	private static final long serialVersionUID = 1L;
	private String matricula;
	private Set<AlunoTurma> alunoTurmas = new HashSet<AlunoTurma>(0);
	private Usuario usuario;
	private Curriculo curriculo;
	private SituacaoAcademica situacaoAcademica;
	private FormaIngresso formaIngresso;

	public Aluno() {
		situacaoAcademica = SituacaoAcademica.REGULAR;
	}

	public Aluno(String matricula) {
		this.matricula = matricula;
	}

	public Aluno(String matricula, Usuario usuario,
			Set<AlunoTurma> alunoTurmas, Curriculo curriculo, SituacaoAcademica situacaoAcademica,
			FormaIngresso formaIngresso) {
		this.matricula = matricula;
		this.alunoTurmas = alunoTurmas;
		this.usuario = usuario;
		this.curriculo = curriculo;
		this.situacaoAcademica = situacaoAcademica;
		this.formaIngresso = formaIngresso;
	}

	@Id
	@Column(name = "matricula", unique = true, nullable = false, length = 9)
	@NotNull
	@Length(max = 9)
	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "aluno")
	public Set<AlunoTurma> getAlunoTurmas() {
		return this.alunoTurmas;
	}

	public void setAlunoTurmas(Set<AlunoTurma> alunoTurmas) {
		this.alunoTurmas = alunoTurmas;
	}

	@OneToOne(mappedBy="aluno")
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade= {CascadeType.ALL})
	@JoinColumn(name = "id_curriculo")
	public Curriculo getCurriculo() {
		return curriculo;
	}
	
	public void setCurriculo(Curriculo curriculo) {
		this.curriculo = curriculo;
	}

	@Column(name = "situacao_academica")
	public SituacaoAcademica getSituacaoAcademica() {
		return situacaoAcademica;
	}

	public void setSituacaoAcademica(SituacaoAcademica situacaoAcademica) {
		this.situacaoAcademica = situacaoAcademica;
	}

	@Column(name = "forma_ingresso")
	public FormaIngresso getFormaIngresso() {
		return formaIngresso;
	}

	public void setFormaIngresso(FormaIngresso formaIngresso) {
		this.formaIngresso = formaIngresso;
	}

}
