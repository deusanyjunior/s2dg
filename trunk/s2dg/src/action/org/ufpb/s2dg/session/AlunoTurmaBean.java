package org.ufpb.s2dg.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.AlunoTurma;
import org.ufpb.s2dg.entity.Professor;

@Name("alunoTurmaBean")
@Scope(ScopeType.PAGE)
@AutoCreate
public class AlunoTurmaBean {

	private AlunoTurma alunoTurma;
	
	@In
	private Fachada fachada;

	public AlunoTurma getAlunoTurma() {
		return alunoTurma;
	}

	public void setAlunoTurma(AlunoTurma alunoTurma) {
		this.alunoTurma = alunoTurma;
		if(alunoTurma != null)
			fachada.setTurma(alunoTurma.getTurma());
	}
	
	public List<AlunoTurma> getAlunoTurmaAsList() {
		if(alunoTurma != null) {
			List<AlunoTurma> list = new ArrayList<AlunoTurma>();
			list.add(alunoTurma);
			return list;
		}
		else return null;
	}
	
	public Set<Professor> getProfessores(){
		return null;
//		return this.getAlunoTurma().getTurma().getProfessores();
	}
	
}
