package org.ufpb.s2dg.session;

import org.ufpb.s2dg.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("turmaHome")
public class TurmaHome extends EntityHome<Turma> {

	@In(create = true)
	ProfessorHome professorHome;
	@In(create = true)
	PeriodoHome periodoHome;
	@In(create = true)
	DisciplinaHome disciplinaHome;

	public void setTurmaId(Long id) {
		setId(id);
	}

	public Long getTurmaId() {
		return (Long) getId();
	}

	@Override
	protected Turma createInstance() {
		Turma turma = new Turma();
		return turma;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Professor professor = professorHome.getDefinedInstance();
		if (professor != null) {
			getInstance().setProfessor(professor);
		}
		Periodo periodo = periodoHome.getDefinedInstance();
		if (periodo != null) {
			getInstance().setPeriodo(periodo);
		}
		Disciplina disciplina = disciplinaHome.getDefinedInstance();
		if (disciplina != null) {
			getInstance().setDisciplina(disciplina);
		}
	}

	public boolean isWired() {
		if (getInstance().getDisciplina() == null)
			return false;
		return true;
	}

	public Turma getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<AlunoTurma> getAlunoTurmas() {
		return getInstance() == null ? null : new ArrayList<AlunoTurma>(
				getInstance().getAlunoTurmas());
	}

}
