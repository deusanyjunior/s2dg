package org.ufpb.s2dg.session.persistence;

import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.ufpb.s2dg.entity.Professor;
import org.ufpb.s2dg.entity.Turma;

@AutoCreate
@Name("turmaDAO")
public class TurmaDAO {

	@In
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Turma> getTurmas(Professor professor) {
		return (List<Turma>) entityManager.createQuery(
    	"select turma from Turma as turma where turma.professor = :professor order by turma.disciplina.nome")
    	.setParameter("professor", professor)
    	.getResultList();
	}
	
	
	
}
