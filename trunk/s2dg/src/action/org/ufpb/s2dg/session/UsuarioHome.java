package org.ufpb.s2dg.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.ufpb.s2dg.entity.*;

@Name("usuarioHome")
public class UsuarioHome extends EntityHome<Usuario> {

	private static final long serialVersionUID = 1L;

	public void setUsuarioId(Long id) {
		setId(id);
	}

	public Long getUsuarioId() {
		return (Long) getId();
	}

	@Override
	protected Usuario createInstance() {
		Usuario usuario = new Usuario();
		return usuario;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Usuario getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
