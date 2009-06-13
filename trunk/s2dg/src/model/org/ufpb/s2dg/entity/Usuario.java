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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private Aluno aluno;
	private Professor professor;
	private String nome;
	private String cpf;
	private String senha;
	private Set<Role> roles = new HashSet<Role>(0);

	public Usuario() {}

	public Usuario(long id) {
		this.id = id;
	}

	public Usuario(long id, Aluno aluno, Professor professor, String nome,
			Set<Role> roles) {
		this.id = id;
		this.aluno = aluno;
		this.professor = professor;
		this.nome = nome;
		this.roles = roles;
	}

	@Id
	@Column(name = "id", 
			unique = true, 
			nullable = false)
	@NotNull
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.EAGER)
	public Aluno getAluno() {
		return this.aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	@OneToOne(fetch = FetchType.EAGER)
	public Professor getProfessor() {
		return this.professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	@Column(name = "nome", length = 50)
	@Length(max = 50)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_role", 
			joinColumns = { 
			 @JoinColumn(name = "usuario_id", 
					 	 nullable = false, 
					 	 updatable = false) 
			}, 
			inverseJoinColumns = { 
			 @JoinColumn(name = "role_id", 
					 	 nullable = false, 
					 	 updatable = false) 
	})
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
