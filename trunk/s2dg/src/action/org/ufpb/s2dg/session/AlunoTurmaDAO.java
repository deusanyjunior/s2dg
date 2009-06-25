package org.ufpb.s2dg.session;

import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.ufpb.s2dg.entity.Aluno;
import org.ufpb.s2dg.entity.AlunoTurma;
import org.ufpb.s2dg.entity.Turma;

@AutoCreate
@Name("alunoTurmaDAO")
public class AlunoTurmaDAO {

	@In
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<AlunoTurma> getAlunoTurmas(Aluno aluno) {
		return (List<AlunoTurma>) entityManager.createQuery(
    	"select alunoTurma from AlunoTurma as alunoTurma where alunoTurma.aluno = :aluno order by alunoTurma.turma.disciplina.nome")
    	.setParameter("aluno", aluno)
    	.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<AlunoTurma> getAlunoTurmas(Turma turma) {
		return (List<AlunoTurma>) entityManager.createQuery(
    	"select alunoTurma from AlunoTurma as alunoTurma where alunoTurma.turma = :turma")
    	.setParameter("turma", turma)
    	.getResultList();
	}

	public void atualiza(AlunoTurma alunoTurma) {
		entityManager.merge(alunoTurma);
	}
	
	
	
}
