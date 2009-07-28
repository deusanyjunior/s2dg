package org.ufpb.s2dg.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.ufpb.s2dg.entity.Professor;
import org.ufpb.s2dg.entity.Turma;

@Name("turmaBean")
@Scope(ScopeType.SESSION)
@AutoCreate
public class TurmaBean {

	Turma turma;
	@In
	Fachada fachada;
	@In
	FacesMessages facesMessages;

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
		else {
			turma.setCalcularMediaAutomaticamente(true);
			fachada.atualizaAlunoTurmas();
		}
	}
	
	public void atualiza() {
		fachada.atualizaTurma();
	}
	
	public void atualizaPlanoDeCurso() {
		atualiza();
		facesMessages.add("Informações salvas com sucesso!");
	}
	
	public List<Professor> listaProfessores() {
		if(turma != null) {
			Set<Professor> setProfessores = turma.getProfessores();
			if(setProfessores != null) {
				List<Professor> listProfessores = new ArrayList<Professor>(setProfessores);
				for(Professor professor: listProfessores) {
					professor.setUsuario(fachada.getUsuarioProfessor(professor.getMatricula()));
				}
				Collections.sort(listProfessores,new ProfessorComparator());
				return listProfessores;
			}
		}
		return null;
	}
	
}
