package org.ufpb.s2dg.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.Calendario;

@Name("calendarioBean")
@Scope(ScopeType.SESSION)
@AutoCreate
public class CalendarioBean {

	Calendario calendario;
	
	@In
	Fachada fachada;
	
	@Create
	public void init() {
		calendario = fachada.getCalendarioDoBanco();
	}

	public Calendario getCalendario() {
		return calendario;
	}

	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}
	
}
