package org.ufpb.s2dg.session.persistence;

import java.util.Date;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.ufpb.s2dg.entity.Log;
import org.ufpb.s2dg.entity.Usuario;

@Name("logDAO")
@AutoCreate
public class LogDAO {

	@In
	EntityManager entityManager;
	
	public void cria(String mensagem, Usuario usuario) {
		Log log = new Log();
		log.setMensagem(mensagem);
		log.setData(new Date());
		log.setCpfUsuario(usuario.getCpf());
		entityManager.persist(log);
	}

}
