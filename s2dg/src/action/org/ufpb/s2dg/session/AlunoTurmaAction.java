package org.ufpb.s2dg.session;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesContext;
import org.ufpb.s2dg.entity.AlunoTurma;
import org.ufpb.s2dg.entity.Usuario;

@Name("alunoTurmaAction")
@Scope(ScopeType.SESSION)
@AutoCreate
public class AlunoTurmaAction{
	
	@In
	EntityManager entityManager;
	@In
	Usuario usuario;
	@In
	FacesContext facesContext;
	
	private List<AlunoTurma> alunoTurmas;
	private float mediaAtual;
	private int frequenciaAtual;
	
	public List<AlunoTurma> getAlunoTurmas() {
		return alunoTurmas;
	}
	
	@Create
	public void init() {
		String ejbql = "Select alunoTurma from AlunoTurma alunoTurma where alunoTurma.aluno = :alunon order by alunoTurma.turma.disciplina";
		alunoTurmas = (ArrayList<AlunoTurma>) entityManager.createQuery(ejbql).setParameter("alunon", usuario.getAluno()).getResultList();
	}
	
	public void atualizaMediaFrequencia(float media, int frequencia) {
		mediaAtual = media;
		frequenciaAtual = frequencia;
	}

	public float getMediaAtual() {
		return mediaAtual;
	}

	public int getFrequenciaAtual() {
		return frequenciaAtual;
	}
	
}
