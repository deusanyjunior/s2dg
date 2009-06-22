package org.ufpb.s2dg.session;

import org.ufpb.s2dg.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("disciplinaHome")
public class DisciplinaHome extends EntityHome<Disciplina> {

	public void setDisciplinaCodigo(String id) {
		setId(id);
	}

	public String getDisciplinaCodigo() {
		return (String) getId();
	}

	@Override
	protected Disciplina createInstance() {
		Disciplina disciplina = new Disciplina();
		return disciplina;
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

	public Disciplina getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Turma> getTurmas() {
		return getInstance() == null ? null : new ArrayList<Turma>(
				getInstance().getTurmas());
	}

}
