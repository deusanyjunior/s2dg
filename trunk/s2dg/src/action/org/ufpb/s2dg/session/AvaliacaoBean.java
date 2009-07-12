package org.ufpb.s2dg.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.Avaliacao;
import org.ufpb.s2dg.entity.Turma;

@Name("avaliacaoBean")
@Scope(ScopeType.PAGE)
@AutoCreate
public class AvaliacaoBean {

	private Avaliacao avaliacao;
	private boolean criarOuEditar = true;
	private Avaliacao avaliacaoParaExclusao;
	
	@In
	Fachada fachada;

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public boolean isCriarOuEditar() {
		return criarOuEditar;
	}

	public void setCriarOuEditar(boolean criarOuEditar) {
		this.criarOuEditar = criarOuEditar;
	}
	
	public void setAvaliacaoParaEdicao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
		criarOuEditar = false;
	}
	
	public void cancelarEdicao() {
		this.avaliacao = new Avaliacao();
		criarOuEditar = true;
	}
	
	public Avaliacao getAvaliacaoParaExclusao() {
		return avaliacaoParaExclusao;
	}

	public void setAvaliacaoParaExclusao(Avaliacao avaliacaoParaExclusao) {
		this.avaliacaoParaExclusao = avaliacaoParaExclusao;
	}
	
	public void atualizarAvaliacao() {
		fachada.atualizaAvaliacao(avaliacao);
		this.avaliacao = new Avaliacao();
		criarOuEditar = true;
		fachada.initAvaliacoes();
	}
	
	public void cancelaExclusao() {
		this.avaliacao = new Avaliacao();
	}
	
	public void excluiAvaliacao() {
		if(avaliacaoParaExclusao != null) {
			avaliacaoParaExclusao.setTurma(fachada.getTurma());
			fachada.excluiAvaliacao(avaliacaoParaExclusao);
			avaliacaoParaExclusao = null;
		}
		fachada.initAvaliacoes();
	}
	
	public void criarAvaliacao() {
		Turma turmaAtual = fachada.getTurma();
		if((avaliacao != null)&&(turmaAtual != null)) {
			fachada.criaAvaliacao(avaliacao);
			avaliacao = new Avaliacao();
			turmaAtual.setCalcularMediaAutomaticamente(true);
		}
		fachada.initAvaliacoes();
	}
	
}
