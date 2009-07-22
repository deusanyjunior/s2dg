package org.ufpb.s2dg.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.AlunoTurma;
import org.ufpb.s2dg.entity.AlunoTurmaAvaliacao;
import org.ufpb.s2dg.entity.Avaliacao;
import org.ufpb.s2dg.entity.Turma;

@Name("alunoTurmasBean")
@Scope(ScopeType.SESSION)
@AutoCreate
public class AlunoTurmasBean {

	private List<AlunoTurma> alunoTurmas;
	
	@In
	private Fachada fachada;
	@In
	private FacesContext facesContext;

	public void init() {
		Turma turmaAtual = fachada.getTurma();
		if(turmaAtual != null) {
			alunoTurmas = fachada.getAlunoTurmasDoBanco();
			if(alunoTurmas != null) {
				for(int i = 0; i < alunoTurmas.size(); i++) {
					alunoTurmas.get(i).getAluno().setUsuario(fachada.getUsuarioAluno(alunoTurmas.get(i).getAluno().getMatricula()));
				}
				Collections.sort(alunoTurmas, new AlunoTurmaComparator());
			}
		}
	}
	
	public List<AlunoTurma> getAlunoTurmas() {
		fachada.setAlunoTurmaAvaliacoes(new ArrayList<AlunoTurmaAvaliacao>());
		return alunoTurmas;
	}

	public void setAlunoTurmas(List<AlunoTurma> alunoTurmas) {
		this.alunoTurmas = alunoTurmas;
	}
	
	public void atualizaAlunoTurmas() {
		Turma turmaAtual = fachada.getTurma();
		if(turmaAtual != null) {
			fachada.atualizaTurma();
			List<AlunoTurmaAvaliacao> alunoTurmaAvaliacoes = fachada.getAlunoTurmaAvaliacoes();
			if(alunoTurmaAvaliacoes != null) {
				for(int i = 0; i < alunoTurmaAvaliacoes.size(); i++)
					fachada.atualizaAlunoTurmaAvaliacao(alunoTurmaAvaliacoes.get(i));
			}
			if((turmaAtual.isCalcularMediaAutomaticamente())&&(alunoTurmas != null))
				if(alunoTurmas.size() > 0)
					calculaMedias();
			if(alunoTurmas != null) {
				for(int i = 0; i < alunoTurmas.size(); i++)
					fachada.atualizaAlunoTurma(alunoTurmas.get(i));
			}
			facesContext.addMessage("form_avaliacoes:checkboxmedia", new FacesMessage("Informações salvas com sucesso!"));
		}
	}
	
	private void calculaMedias() {
		if(alunoTurmas != null) {
			for(int i = 0; i < alunoTurmas.size(); i++) {
				float somaNota = 0;
				float somaPeso = 0;
				List<Avaliacao> avaliacoes = fachada.getAvaliacoes();
				for(int j = 0; j < avaliacoes.size(); j++) {
					somaNota += fachada.getAlunoTurmaAvaliacao(alunoTurmas.get(i), avaliacoes.get(j)).getNota()
					* avaliacoes.get(j).getPeso();
					somaPeso += avaliacoes.get(j).getPeso();
				}
				alunoTurmas.get(i).setMedia(somaNota/somaPeso);
			}
		}
	}
	
}
