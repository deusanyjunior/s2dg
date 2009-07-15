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
    	"select turma from Turma as turma where turma.periodo = :periodo order by turma.disciplina.nome")
//    	.setParameter("professor", professor)
    	.setParameter("periodo",periodo)
    	.getResultList();
		/* Gambiarra para retornar a lista de turmas do professor */
		int i = 0;
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		System.out.println(list.size());
		while( i < list.size()){
			if(!list.get(i).getProfessores().contains(professor)){
				list.remove(i);
			} else {
				i++;
			}
		}		
		System.out.println(list.size());
		/**/
		return list;
	}
	public void atualiza(Turma turmaAtual) {
		entityManager.merge(turmaAtual);
	}
	
}
