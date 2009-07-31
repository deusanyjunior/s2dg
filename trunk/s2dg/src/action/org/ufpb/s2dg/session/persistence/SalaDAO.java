package org.ufpb.s2dg.session.persistence;

import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.ufpb.s2dg.entity.Sala;
import org.ufpb.s2dg.entity.Turma;

@AutoCreate
@Name("salaDAO")
public class SalaDAO {
	@In
	EntityManager entityManager;
	
	public List<Sala> getSalas(Turma turma) {
		List<Sala> list = (List<Sala>) entityManager.createQuery(""
    	/*"select sala from Sala as sala where turma.periodo = :periodo and :professor member of turma.professores order by turma.disciplina.nome"*/)
    	.setParameter("turma", turma)
    	.getResultList();
		
		return list;
	}
}
