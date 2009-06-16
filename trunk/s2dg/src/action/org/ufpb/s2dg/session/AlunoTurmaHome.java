package org.ufpb.s2dg.session;

import org.ufpb.s2dg.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("alunoTurmaHome")
public class AlunoTurmaHome extends EntityHome<AlunoTurma> {

	@In(create = true)
	TurmaHome turmaHome;
	@In(create = true)
	AlunoHome alunoHome;

	public void setAlunoTurmaId(AlunoTurmaId id) {
		setId(id);
	}

	public AlunoTurmaId getAlunoTurmaId() {
		return (AlunoTurmaId) getId();
	}

	public AlunoTurmaHome() {
		setAlunoTurmaId(new AlunoTurmaId());
	}

	@Override
	public boolean isIdDefined() {
		if (getAlunoTurmaId().getMatriculaAluno() == null
				|| "".equals(getAlunoTurmaId().getMatriculaAluno()))
			return false;
		if (getAlunoTurmaId().getIdTurma() == 0)
			return false;
		return true;
	}

	@Override
	protected AlunoTurma createInstance() {
		AlunoTurma alunoTurma = new AlunoTurma();
		alunoTurma.setId(new AlunoTurmaId());
		return alunoTurma;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Turma turma = turmaHome.getDefinedInstance();
		if (turma != null) {
			getInstance().setTurma(turma);
		}
		Aluno aluno = alunoHome.getDefinedInstance();
		if (aluno != null) {
			getInstance().setAluno(aluno);
		}
	}

	public boolean isWired() {
		if (getInstance().getTurma() == null)
			return false;
		if (getInstance().getAluno() == null)
			return false;
		return true;
	}

	public AlunoTurma getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
