package org.ufpb.s2dg.session;

import org.ufpb.s2dg.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("periodoHome")
public class PeriodoHome extends EntityHome<Periodo> {

	public void setPeriodoId(Long id) {
		setId(id);
	}

	public Long getPeriodoId() {
		return (Long) getId();
	}

	@Override
	protected Periodo createInstance() {
		Periodo periodo = new Periodo();
		return periodo;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Periodo getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Turma> getTurmas() {
		return getInstance() == null ? null : new ArrayList<Turma>(
				getInstance().getTurmas());
	}

}
