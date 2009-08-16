package org.ufpb.s2dg.session.persistence;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.ufpb.s2dg.entity.Professor;
import org.ufpb.s2dg.entity.Usuario;

@Name("professorDAO")
@AutoCreate
public class ProfessorDAO {

	@In
	EntityManager entityManager;

	public Professor getProfessor(Usuario usuario) {
		return (Professor) entityManager.createQuery(
    	"select p from Professor as p where p.usuario =:usuario")
    	.setParameter("usuario", usuario)
    	.getSingleResult();
	}
	
	
	
}
