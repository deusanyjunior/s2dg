package org.ufpb.s2dg.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.Turma;

@Name("turmaBean")
@Scope(ScopeType.PAGE)
@AutoCreate
public class TurmaBean {

	Turma turma;
	@In
	Fachada fachada;

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
		fachada.cancelarEdicaoDeAvaliacao();
		fachada.initAvaliacoes();
		fachada.initAlunoTurmas();
	}
	
	public void switchCalcMediaAuto() {
		if (turma.isCalcularMediaAutomaticamente())
			turma.setCalcularMediaAutomaticamente(false);
		else turma.setCalcularMediaAutomaticamente(true);
	}
	
	public void atualiza() {
		fachada.atualizaTurma();
	}
	
}
