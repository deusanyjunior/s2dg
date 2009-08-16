package org.ufpb.s2dg.session.persistence;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.ufpb.s2dg.entity.Curriculo;
import org.ufpb.s2dg.entity.Curso;

@AutoCreate
@Name("cursoDAO")
public class CursoDAO {

	@In
	EntityManager entityManager;
	
	public Curso getCurso(Curriculo curriculo) {
		Query query = entityManager.createQuery(
    		"select curso from Curso as curso where :curriculo member of curso.curriculos")
    		.setParameter("curriculo", curriculo);
		ArrayList<Curso> result = (ArrayList<Curso>) query.getResultList();
		if (result.size() > 0)
			return result.get(0);
		return null;
	}
	
}
