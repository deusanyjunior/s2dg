package org.ufpb.s2dg.session.beans;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.AlunoTurma;
import org.ufpb.s2dg.entity.Calendario;
import org.ufpb.s2dg.entity.Centro;
import org.ufpb.s2dg.entity.Periodo;
import org.ufpb.s2dg.entity.Professor;
import org.ufpb.s2dg.entity.Role;
import org.ufpb.s2dg.entity.Usuario;
import org.ufpb.s2dg.session.Fachada;

@Name("usuarioBean")
@AutoCreate
@Scope(ScopeType.SESSION)
public class UsuarioBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	
	@In
	Fachada fachada;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
		defineCalendarios();	
	}
	
	private void defineCalendarios() {
		for(Role r: usuario.getRoles()){
        	defineCalendario(r);
        }
	}
	
	public Usuario getUsuarioProfessor() {
		AlunoTurma alunoTurmaAtual = fachada.getAlunoTurma();
		if (alunoTurmaAtual == null)
			return null;
		Professor professor = (Professor)alunoTurmaAtual.getTurma().getProfessores().toArray()[0];
		return fachada.getUsuarioProfessor(professor.getMatricula());
	}
	
	public void defineCalendario(Role tipoUsuario){
		if(tipoUsuario.getNome().equals("professor"))
			defineCalendarioProfessor();
		else
			defineCalendarioAluno();
	}
	
	/* TODO Clodoaldo: Adicionar método para pegar fornecer calendario ativo - professor ou aluno.
	 * Ver classe ModeloCalendario para mais detalhes.
	 */
	
	private void defineCalendarioAluno() {
		Periodo periodoAtual = fachada.getPeriodoAtual();
		Centro centroAluno = getCentroAluno();
		Calendario calendarioAluno = fachada.getCalendarioDoBanco(periodoAtual, centroAluno);
		fachada.setCalendarioAluno(calendarioAluno);
	}
	
	private void defineCalendarioProfessor() {
		Periodo periodoAtual = fachada.getPeriodoAtual();
		Centro centroProfessor = getCentroProfessor();
		Calendario calendarioProfessor = fachada.getCalendarioDoBanco(periodoAtual, centroProfessor);
		fachada.setCalendarioProfessor(calendarioProfessor);
	}
	
	private Centro getCentroAluno() {
		// TODO Clodoaldo: Remove Middle Man?
		return usuario.getAluno().getCurriculo().getCurso().getCentro();
	}
	
	private Centro getCentroProfessor() {
		// TODO Clodoaldo: Remove Middle Man?
		return usuario.getProfessor().getDepartamento().getCentro();
	}
	
}
