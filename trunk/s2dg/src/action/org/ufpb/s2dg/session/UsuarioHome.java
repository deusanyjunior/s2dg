package org.ufpb.s2dg.session;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.ufpb.s2dg.entity.Aluno;
import org.ufpb.s2dg.entity.Professor;
import org.ufpb.s2dg.entity.Usuario;

@Name("usuarioHome")
public class UsuarioHome extends EntityHome<Usuario> {

	@In(create = true)
	AlunoHome alunoHome;
	@In(create = true)
	ProfessorHome professorHome;

	public void setUsuarioId(Long id) {
		setId(id);
	}

	public Long getUsuarioId() {
		return (Long) getId();
	}

	@Override
	protected Usuario createInstance() {
		Usuario usuario = new Usuario();
		return usuario;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Aluno aluno = alunoHome.getDefinedInstance();
		if (aluno != null) {
			getInstance().setAluno(aluno);
		}
		Professor professor = professorHome.getDefinedInstance();
		if (professor != null) {
			getInstance().setProfessor(professor);
		}
	}

	public boolean isWired() {
		return true;
	}

	public Usuario getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
