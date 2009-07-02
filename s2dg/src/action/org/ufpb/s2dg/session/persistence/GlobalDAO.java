package org.ufpb.s2dg.session.persistence;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.ufpb.s2dg.entity.Global;

@AutoCreate
@Name("globalDAO")
public class GlobalDAO {

	@In
	EntityManager entityManager;
	
	public Global getGlobal() {
		return (Global) entityManager.createQuery(
    	"select global from Global as global")
    	.getSingleResult();
	}

}
