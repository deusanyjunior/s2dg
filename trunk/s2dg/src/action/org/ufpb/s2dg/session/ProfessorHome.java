package org.ufpb.s2dg.session;

import org.ufpb.s2dg.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("professorHome")
public class ProfessorHome extends EntityHome<Professor> {

	public void setProfessorMatricula(String id) {
		setId(id);
	}

	public String getProfessorMatricula() {
		return (String) getId();
	}

	@Override
	protected Professor createInstance() {
		Professor professor = new Professor();
		return professor;
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

	public Professor getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Usuario> getUsuarios() {
		return getInstance() == null ? null : new ArrayList<Usuario>(
				getInstance().getUsuarios());
	}

	public List<Turma> getTurmas() {
		return getInstance() == null ? null : new ArrayList<Turma>(
				getInstance().getTurmas());
	}

}
