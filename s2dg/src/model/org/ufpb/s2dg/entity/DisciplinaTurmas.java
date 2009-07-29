package org.ufpb.s2dg.entity;

import java.util.ArrayList;
import java.util.List;

public class DisciplinaTurmas {

	private Disciplina disciplina;
	private List<Turma> turmas;
	
	public DisciplinaTurmas(Disciplina disciplina, List<Turma> turmas) {
		this.disciplina = disciplina;
		this.turmas = turmas;
	}
	
	public Disciplina getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
	public List<Turma> getTurmas() {
		return turmas;
	}
	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}
	
	public static List<DisciplinaTurmas> geraTurmasPorDisciplina(
			List<Turma> turmas) {
		
		if((turmas == null)||(turmas.size() == 0))
			return null;
	
		/* PRESSUPOSTO: A lista de turmas já vem do banco ordenada por disciplina */
		
		List<DisciplinaTurmas> disciplinaTurmas = new ArrayList<DisciplinaTurmas>();
		Disciplina disciplinaAnterior = null;
		List<Turma> turmasPorDisciplina = null;
		
		for(int i = 0; i < turmas.size(); i++) {
			Turma turmaAtual = turmas.get(i); 
			if(turmaAtual.getDisciplina() != disciplinaAnterior) {
				if(turmasPorDisciplina != null) {
					disciplinaTurmas.add(new DisciplinaTurmas(disciplinaAnterior,turmasPorDisciplina));
				}
				turmasPorDisciplina =  new ArrayList<Turma>();
			}
			turmasPorDisciplina.add(turmaAtual);
			disciplinaAnterior = turmaAtual.getDisciplina();
		}
		
		disciplinaTurmas.add(new DisciplinaTurmas(disciplinaAnterior,turmasPorDisciplina));
		
		return disciplinaTurmas;
		
	}
	
	public List<Turma> listaTurmasSemUltima() {
		List<Turma> temp = new ArrayList<Turma>(turmas);
		temp.remove(turmas.size()-1);
		return temp;
	}
	
	public Turma getUltimaTurma() {
		return turmas.get(turmas.size()-1);
	}
	
}
