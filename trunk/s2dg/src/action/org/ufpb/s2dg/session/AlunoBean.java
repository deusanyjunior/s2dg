package org.ufpb.s2dg.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.Aluno;

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

}
