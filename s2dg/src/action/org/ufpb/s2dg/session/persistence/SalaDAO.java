package org.ufpb.s2dg.session.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
	
	public List<Sala> getSalas(long id) {
		Turma list = (Turma)entityManager.createQuery("select t from Turma as t where t.id = :id")
		.setParameter("id", id).getSingleResult();
		
		Set<Sala> conjunto = list.getSalas();
		
		if (conjunto.size() == 0) {
			return null;
		}
		
		ArrayList<Sala> salas = new ArrayList<Sala>();
		
		for (Sala s : conjunto) {
			salas.add(s);
		}
		
		return salas;
	}
}
