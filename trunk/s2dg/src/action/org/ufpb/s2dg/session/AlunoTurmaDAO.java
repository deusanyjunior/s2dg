package org.ufpb.s2dg.session;

import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.ufpb.s2dg.entity.AlunoTurma;
import org.ufpb.s2dg.entity.Usuario;

@AutoCreate
@Name("alunoTurmaDAO")
public class AlunoTurmaDAO {

	@In
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<AlunoTurma> getAlunoTurmas(Usuario usuario) {
		return (List<AlunoTurma>) entityManager.createQuery(
    	"select alunoTurma from AlunoTurma as alunoTurma where alunoTurma.aluno = :aluno order by alunoTurma.turma.disciplina.nome")
    	.setParameter("aluno", usuario.getAluno())
    	.getResultList();
	}
	
	
	
}
