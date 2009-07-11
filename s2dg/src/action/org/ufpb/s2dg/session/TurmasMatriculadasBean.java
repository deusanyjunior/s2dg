package org.ufpb.s2dg.session;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.AlunoTurma;

@Name("turmasMatriculadasBean")
@Scope(ScopeType.SESSION)
@AutoCreate
public class TurmasMatriculadasBean {

	private List<AlunoTurma> alunoTurmas;
	
	@In
	Fachada fachada;
	
	@Create
	public void init() {
		List<AlunoTurma> ats = fachada.getAlunoTurmaDoBanco();
		if(ats != null) {
			if (ats.size() > 0) {
				fachada.setAlunoTurma(ats.get(0));
				this.alunoTurmas = ats;
			}
		}
	}

	public List<AlunoTurma> getAlunoTurmas() {
		return alunoTurmas;
	}

	public void setAlunoTurmas(List<AlunoTurma> alunoTurmas) {
		this.alunoTurmas = alunoTurmas;
	}
	
}
