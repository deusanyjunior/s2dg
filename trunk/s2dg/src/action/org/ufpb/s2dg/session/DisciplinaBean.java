package org.ufpb.s2dg.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.Disciplina;

@Name("disciplinaBean")
@Scope(ScopeType.SESSION)
@AutoCreate
public class DisciplinaBean {

	@In
	Fachada fachada;
	
	
}
