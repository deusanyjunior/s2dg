package org.ufpb.s2dg.session.persistence;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.ufpb.s2dg.entity.Calendario;
import org.ufpb.s2dg.entity.Periodo;

@AutoCreate
@Name("calendarioDAO")
public class CalendarioDAO {

	@In
	EntityManager entityManager;
	
	public Calendario getCalendario(Periodo periodo) {
		return (Calendario) entityManager.createQuery(
    	"select cal from Calendario as cal where cal.periodo = :periodo")
    	.setParameter("periodo", periodo)
    	.getSingleResult();
	}

}
