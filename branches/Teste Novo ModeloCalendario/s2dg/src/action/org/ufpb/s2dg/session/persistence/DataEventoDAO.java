package org.ufpb.s2dg.session.persistence;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import org.ufpb.s2dg.entity.DataEvento;

@AutoCreate
@Name("dataeventoDAO")
public class DataEventoDAO {
	
	@In(required = true)
	EntityManager entityManager;
	
	public ArrayList<DataEvento> getDataEvento(int mes, int ano) {
		try {
			if (entityManager == null) {
				System.out.println("1111111111111111111");
			}
			else if (entityManager.createQuery(
					"select de from DataEvento as de where EXTRACT(MONTH FROM de.data) = " + 4 + " and " +
					"EXTRACT(YEAR FROM de.data) = " + 2009) == null) {
				System.out.println("222222222222222222222222");
			}
			else  if((ArrayList<DataEvento>) entityManager.createQuery(
					"select de from DataEvento as de where EXTRACT(MONTH FROM de.data) = " + 4 + " and " +
					"EXTRACT(YEAR FROM de.data) = " + 2009).
					setParameter("mes", mes).setParameter("ano", ano).getResultList() == null) {
				System.out.println("333333333333333333333333");
			}
			
			return (ArrayList<DataEvento>) entityManager.createQuery(
					"select de from DataEvento as de where EXTRACT(MONTH FROM de.data) = :mes and " +
					"EXTRACT(YEAR FROM de.data) = :ano")
					.setParameter("mes", mes).setParameter("ano", ano).
					getResultList();
		}
		catch(NullPointerException npe) {
			npe.printStackTrace();
			return new ArrayList<DataEvento>();
		}
		
	}
}
