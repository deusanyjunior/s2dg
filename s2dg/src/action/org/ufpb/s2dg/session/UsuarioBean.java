package org.ufpb.s2dg.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.AlunoTurma;
import org.ufpb.s2dg.entity.Professor;
import org.ufpb.s2dg.entity.Usuario;

@Name("usuarioBean")
@AutoCreate
@Scope(ScopeType.SESSION)
public class UsuarioBean {

	private Usuario usuario;
	
	@In
	Fachada fachada;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Usuario getUsuarioProfessor() {
		AlunoTurma alunoTurmaAtual = fachada.getAlunoTurma();
		if (alunoTurmaAtual == null)
			return null;
		Professor professor = (Professor)alunoTurmaAtual.getTurma().getProfessores().toArray()[0];
		return fachada.getUsuarioProfessor(professor.getMatricula());
	}
	
}
