package org.ufpb.s2dg.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.Aluno;
import org.ufpb.s2dg.entity.Aluno.FormaIngresso;
import org.ufpb.s2dg.entity.Aluno.SituacaoAcademica;

@Name("alunoBean")
@Scope(ScopeType.SESSION)
@AutoCreate
public class AlunoBean {
	
	private Aluno aluno;
	
	@In
	private Fachada fachada;
	
	public Aluno getAluno() {
		return aluno;
	}
	
	public Boolean situacaoAcademicaGraduado(){
		
		if(fachada.getAluno().getSituacaoAcademica()==SituacaoAcademica.GRADUADO){
			return true;
		}
		else
			return false;
	}
	
	public Boolean formaIngresso(){
		
		if(fachada.getAluno().getFormaIngresso()==FormaIngresso.VESTIBULAR){
			return true;
		}
		else
			return false;
	}
}
