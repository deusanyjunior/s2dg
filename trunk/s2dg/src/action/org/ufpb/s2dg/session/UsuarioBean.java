package org.ufpb.s2dg.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.Usuario;

@Name("usuarioBean")
@AutoCreate
@Scope(ScopeType.SESSION)
public class UsuarioBean {

	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
		
}
