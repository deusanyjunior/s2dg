package org.ufpb.s2dg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

@Entity
@Table(name = "disciplina_vestibular")
public class DisciplinaVestibular implements Serializable{
	private static final long serialVersionUID = 1L;
	private long id;
	private Aluno aluno;
	private String nome;
	private float media;
	
	public DisciplinaVestibular() {}
	
	public DisciplinaVestibular(long id, Aluno aluno, String nome, float media) {
		this.id = id;
		this.aluno = aluno;
		this.nome = nome;
		this.media = media;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "matricula_aluno", nullable = false)
	@NotNull
	public Aluno getAluno() {
		return this.aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	@Column(name = "nome")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "media")
	public float getMedia() {
		return media;
	}

	public void setMedia(float media) {
		this.media = media;
	}

}
