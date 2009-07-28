package org.ufpb.s2dg.session.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.ufpb.s2dg.entity.Curriculo;
import org.ufpb.s2dg.entity.Disciplina;
import org.ufpb.s2dg.entity.Turma;

@Name("disciplinaDAO")
@AutoCreate
public class DisciplinaDAO {

	@In
	EntityManager entityManager;

	public List<Disciplina> getDisciplinas(Curriculo curriculo) {
		if(curriculo != null) {
			Query q = entityManager.createQuery("select d from Disciplina as d where :curriculo member of d.curriculos order by d.nome")
			.setParameter("curriculo", curriculo);
			List<Disciplina> list = q.getResultList();
			if(list.size() > 0)
				return list;
		}
		return null;
	}

	public Disciplina getDisciplinas(Turma t) {
		Query q = entityManager.createQuery("select d from Disciplina as d where :turma member of d.turmas")
		.setParameter("turma", t);
		List<Disciplina> list = q.getResultList();
		if(list.size() > 0)
			return list.get(0);
		return null;
	}

	public List<Disciplina> getCoRequisitos(Disciplina d) {
		Query q = entityManager.createQuery("select d from Disciplina as d where :disciplina member of d.co_requisitos")
		.setParameter("disciplina", d);
		List<Disciplina> list = q.getResultList();
		if(list.size() > 0)
			return list;
		return null;
	}
	
}
