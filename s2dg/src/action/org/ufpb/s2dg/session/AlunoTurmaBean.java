package org.ufpb.s2dg.session;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.AlunoTurma;
import org.ufpb.s2dg.entity.Disciplina;
import org.ufpb.s2dg.entity.Turma;

@Name("alunoTurmaBean")
@Scope(ScopeType.SESSION)
@AutoCreate
public class AlunoTurmaBean {

	private AlunoTurma alunoTurma;
	
	@In
	private Fachada fachada;

	@In
	MenuDiscenteAction MenuDiscenteAction;
	
	public AlunoTurma getAlunoTurma() {
		return alunoTurma;
	}

	public void setAlunoTurma(AlunoTurma alunoTurma) {
		MenuDiscenteAction.setId_Menu(0);
		this.alunoTurma = alunoTurma;
		if(alunoTurma != null) {
			Turma t = alunoTurma.getTurma();
			if(t == null)
				t = fachada.getTurmaDoBanco(alunoTurma);
			Disciplina d = t.getDisciplina();
			if(d == null)
				d = fachada.getDisciplinaDoBanco(t);
			t.setDisciplina(d);
			fachada.setTurma(t);
		}
	}
	
	public List<AlunoTurma> getAlunoTurmaAsList() {
		if(alunoTurma != null) {
			List<AlunoTurma> list = new ArrayList<AlunoTurma>();
			list.add(alunoTurma);
			return list;
		}
		else return null;
	}
	
}
