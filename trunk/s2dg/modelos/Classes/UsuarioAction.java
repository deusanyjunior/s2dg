package org.ufpb.s2dg.session;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.Usuario;

@Name("usuarioAction")
@Scope(ScopeType.SESSION)
@AutoCreate
public class UsuarioAction{
	
	@In
	EntityManager entityManager;
	@Out
	Usuario usuario;
	
	public Usuario getUsuarioFromDB(String cpf) {
		String ejbql = "Select usuario from Usuario usuario where usuario.cpf = :cpfn";
		usuario = (Usuario) entityManager.createQuery(ejbql).setParameter("cpfn", cpf).getSingleResult();
		return usuario;
	}

}
