package org.ufpb.s2dg.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.ufpb.s2dg.entity.*;

import java.util.List;
import java.util.Arrays;

@Name("usuarioList")
public class UsuarioList extends EntityQuery<Usuario> {

	private static final long serialVersionUID = 1L;

	private static final String[] RESTRICTIONS = { "lower(usuario.name) like concat(lower(#{usuarioList.usuario.name}),'%')", };

	private Usuario usuario = new Usuario();

	@Override
	public String getEjbql() {
		return "select usuario from Usuario usuario";
	}

	@Override
	public Integer getMaxResults() {
		return 25;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	@Override
	public List<String> getRestrictions() {
		return Arrays.asList(RESTRICTIONS);
	}

}
