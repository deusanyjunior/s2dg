package org.ufpb.s2dg.session;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.DisciplinaTurmas;
import org.ufpb.s2dg.entity.Turma;

@Name("disciplinasMinistradasBean")
@Scope(ScopeType.SESSION)
@AutoCreate
public class DisciplinasMinistradasBean {

	private List<DisciplinaTurmas> turmasPorDisciplina;
	@In
	Fachada fachada;
	
	@Create
	public void init() {
		List<Turma> turmas = fachada.getTurmasDoBanco();
		if(turmas != null) {
			List<DisciplinaTurmas> disciplinaTurmas = DisciplinaTurmas.geraTurmasPorDisciplina(turmas);
			if(disciplinaTurmas != null) {
				List<Turma> dturmas = disciplinaTurmas.get(0).getTurmas();
				if(dturmas != null)
					fachada.setTurma(dturmas.get(0));
			}
			turmasPorDisciplina = disciplinaTurmas;
		}
	}

	public List<DisciplinaTurmas> getTurmasPorDisciplina() {
		return turmasPorDisciplina;
	}

	public void setTurmasPorDisciplina(List<DisciplinaTurmas> turmasPorDisciplina) {
		this.turmasPorDisciplina = turmasPorDisciplina;
	}
	
}