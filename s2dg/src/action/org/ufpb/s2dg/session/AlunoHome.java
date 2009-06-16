package org.ufpb.s2dg.session;

import org.ufpb.s2dg.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("alunoHome")
public class AlunoHome extends EntityHome<Aluno> {

	public void setAlunoMatricula(String id) {
		setId(id);
	}

	public String getAlunoMatricula() {
		return (String) getId();
	}

	@Override
	protected Aluno createInstance() {
		Aluno aluno = new Aluno();
		return aluno;
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

	public Aluno getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Usuario> getUsuarios() {
		return getInstance() == null ? null : new ArrayList<Usuario>(
				getInstance().getUsuarios());
	}

	public List<AlunoTurma> getAlunoTurmas() {
		return getInstance() == null ? null : new ArrayList<AlunoTurma>(
				getInstance().getAlunoTurmas());
	}

}
