package org.ufpb.s2dg.session.beans;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.session.Fachada;

@Name("disciplinaBean")
@Scope(ScopeType.SESSION)
@AutoCreate
public class DisciplinaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2951633368362212413L;
	@In
	Fachada fachada;
	
	
}
