package org.ufpb.s2dg.session.persistence;

import java.util.Date;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.ufpb.s2dg.entity.Log;

@Name("logDAO")
@AutoCreate
public class LogDAO {

	@In
	EntityManager entityManager;
	
	public void cria(String mensagem) {
		Log log = new Log();
		log.setMensagem(mensagem);
		log.setData(new Date());
		entityManager.persist(log);
	}

}
