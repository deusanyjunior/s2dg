package org.ufpb.s2dg.session.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.ufpb.s2dg.entity.AlunoTurma;
import org.ufpb.s2dg.entity.Disciplina;
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
    	"select turma from Turma as turma where turma.periodo = :periodo and turma.finalizada = :finalizada and :professor member of turma.professores order by turma.disciplina.nome")
    	.setParameter("professor", professor)
    	.setParameter("periodo",periodo)
    	.setParameter("finalizada", new Boolean(false))
    	.getResultList();
		
		return list;
	}
	public void atualiza(Turma turmaAtual) {
		entityManager.merge(turmaAtual);
	}
	@SuppressWarnings("unchecked")
	public Turma getTurma(AlunoTurma at) {
		Query q = entityManager.createQuery("select t from Turma as t where :at member of t.alunoTurmas")
		.setParameter("at", at);
		List<Turma> list = q.getResultList();
		if(list.size() > 0)
			return list.get(0);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Turma> getTurmas(Disciplina d, Periodo p) {
		Query q = entityManager.createQuery("select t from Turma as t where t.disciplina = :disciplina and t.periodo = :periodo")
		.setParameter("disciplina", d)
		.setParameter("periodo",p);
		List<Turma> list = q.getResultList();
		if(list.size() > 0)
			return list;
		return null;
	}
}