package org.ufpb.s2dg.session.persistence;

import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.ufpb.s2dg.entity.Periodo;
import org.ufpb.s2dg.entity.Professor;
import org.ufpb.s2dg.entity.Turma;

@AutoCreate
@Name("turmaDAO")
public class TurmaDAO {

	@In
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Turma> getTurmas(Professor professor, Periodo periodo) {
		List<Turma> list = (List<Turma>) entityManager.createQuery(
    	"select turma from Turma as turma where turma.periodo = :periodo and :professor member of turma.professores order by turma.disciplina.nome")
    	.setParameter("professor", professor)
    	.setParameter("periodo",periodo)
    	.getResultList();
		
		return list;
	}
	public void atualiza(Turma turmaAtual) {
		entityManager.merge(turmaAtual);
	}
	
}
