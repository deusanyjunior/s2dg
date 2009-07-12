package org.ufpb.s2dg.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.Aluno;
import org.ufpb.s2dg.entity.AlunoTurma;
import org.ufpb.s2dg.entity.AlunoTurmaAvaliacao;
import org.ufpb.s2dg.entity.Avaliacao;
import org.ufpb.s2dg.entity.Calendario;
import org.ufpb.s2dg.entity.Global;
import org.ufpb.s2dg.entity.Periodo;
import org.ufpb.s2dg.entity.Professor;
import org.ufpb.s2dg.entity.Turma;
import org.ufpb.s2dg.entity.Usuario;
import org.ufpb.s2dg.session.persistence.AlunoTurmaAvaliacaoDAO;
import org.ufpb.s2dg.session.persistence.AlunoTurmaDAO;
import org.ufpb.s2dg.session.persistence.AvaliacaoDAO;
import org.ufpb.s2dg.session.persistence.CalendarioDAO;
import org.ufpb.s2dg.session.persistence.GlobalDAO;
import org.ufpb.s2dg.session.persistence.ProfessorDAO;
import org.ufpb.s2dg.session.persistence.TurmaDAO;
import org.ufpb.s2dg.session.persistence.UsuarioDAO;

@Name("fachada")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class Fachada {
	
	@In
	private UsuarioDAO usuarioDAO;
	@In
	private AlunoTurmaDAO alunoTurmaDAO;
	@In
	private TurmaDAO turmaDAO;
	@In
	private AvaliacaoDAO avaliacaoDAO;
	@In
	private AlunoTurmaAvaliacaoDAO alunoTurmaAvaliacaoDAO;
	@In
	private GlobalDAO globalDAO;
	@In
	private CalendarioDAO calendarioDAO;
	@In
	private ProfessorDAO professorDAO;
	
	@In
	private UsuarioBean usuarioBean;
	@In
	private GlobalBean globalBean;
	@In
	private TurmaBean turmaBean;
	@In
	private AlunoTurmaBean alunoTurmaBean;
	
	private List<AlunoTurma> alunoTurmas;
	private List<Avaliacao> avaliacoes;
	private Avaliacao avaliacao = new Avaliacao();
	private List<AlunoTurmaAvaliacao> alunoTurmaAvaliacoes;
	private boolean criarOuEditar;
	private Avaliacao avaliacaoParaExclusao;
	
	@Create
	public void init() {
		globalBean.setGlobal(getGlobalDoBanco());
		criarOuEditar = true;
	}
	
	public boolean isCriarOuEditar() {
		return criarOuEditar;
	}

	public void setCriarOuEditar(boolean criarOuEditar) {
		this.criarOuEditar = criarOuEditar;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}
	
	public void setAvaliacaoParaEdicao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
		criarOuEditar = false;
	}
	
	public void cancelarEdicao() {
		this.avaliacao = new Avaliacao();
		criarOuEditar = true;
	}

	public Usuario getUsuarioDoBanco(String username, String password) {
		return usuarioDAO.getUsuario(username,password);
	}
	
	public String getNomeDoProfessorAtual() {
		AlunoTurma alunoTurmaAtual = alunoTurmaBean.getAlunoTurma();
		if (alunoTurmaAtual == null)
			return null;
		return usuarioDAO.getUsuarioProfessor(alunoTurmaAtual.getTurma().getProfessor().getMatricula()).getNome();
	}
	
	public List<Turma> getTurmasDoBanco() {
		Usuario usuario = usuarioBean.getUsuario();
		Periodo periodo = getPeriodoAtual();
		if((usuario != null)&&(periodo != null)) {
			Professor professor = usuario.getProfessor();
			if(professor == null)
				professor = professorDAO.getProfessor(usuario);
			if(professor != null)
				return turmaDAO.getTurmas(professor,periodo);
		}
		return null;
	}

	public void setTurmaAtual(Turma turmaAtual) {
		turmaBean.setTurma(turmaAtual);
		avaliacao = new Avaliacao();
		criarOuEditar = true;
	}
	
	public List<AlunoTurma> getAlunoTurmas() {
		Turma turmaAtual = turmaBean.getTurma();
		if(turmaAtual != null) {
			alunoTurmas = alunoTurmaDAO.getAlunoTurmas(turmaAtual);
			if(alunoTurmas != null) {
				for(int i = 0; i < alunoTurmas.size(); i++) {
					alunoTurmas.get(i).getAluno().setUsuario(getUsuarioAluno(alunoTurmas.get(i).getAluno().getMatricula()));
				}
				Collections.sort(alunoTurmas, new AlunoTurmaComparator());
				alunoTurmaAvaliacoes = new ArrayList<AlunoTurmaAvaliacao>();
				return alunoTurmas;
			}
		}
		return null;
	}
	
	public Usuario getUsuarioAluno(String matricula) {
		return usuarioDAO.getUsuarioAluno(matricula);
	}
	
	public void persisteAlunoTurmas() {
		Turma turmaAtual = turmaBean.getTurma();
		if(turmaAtual != null) {
			turmaDAO.atualiza(turmaAtual);
			if(alunoTurmaAvaliacoes != null) {
				for(int i = 0; i < alunoTurmaAvaliacoes.size(); i++)
					alunoTurmaAvaliacaoDAO.atualiza(alunoTurmaAvaliacoes.get(i));
			}
			if((turmaAtual.isCalcularMediaAutomaticamente())&&(alunoTurmas != null))
				if(alunoTurmas.size() > 0)
					calculaMedias();
			if(alunoTurmas != null) {
				for(int i = 0; i < alunoTurmas.size(); i++)
					alunoTurmaDAO.atualiza(alunoTurmas.get(i));
			}
		}
	}
	
	private void calculaMedias() {
		if(alunoTurmas != null) {
			for(int i = 0; i < alunoTurmas.size(); i++) {
				float somaNota = 0;
				float somaPeso = 0;
				for(int j = 0; j < avaliacoes.size(); j++) {
					somaNota += alunoTurmaAvaliacaoDAO.getAlunoTurmaAvaliacao(alunoTurmas.get(i), avaliacoes.get(j)).getNota()
					* avaliacoes.get(j).getPeso();
					somaPeso += avaliacoes.get(j).getPeso();
				}
				alunoTurmas.get(i).setMedia(somaNota/somaPeso);
			}
		}
	}
	
	public void criarAvaliacao() {
		Turma turmaAtual = turmaBean.getTurma();
		if((avaliacao != null)&&(turmaAtual != null)) {
			avaliacaoDAO.cria(avaliacao, turmaAtual);
			avaliacao = new Avaliacao();
			turmaAtual.setCalcularMediaAutomaticamente(true);
		}
	}
	
	//Criado por Julio e Rennan
	public String getEmail(String cpf) {
		if (cpf.equals("") || cpf == null) {
			System.err.println("usuario:"+cpf);
			return "dienertalencar@gmail.com";
		}
		System.err.println("Chamando getUsuario");
		Usuario user = usuarioDAO.getUsuario(cpf);
		if (user == null)
			return null;
		return user.getEmail();
	}
	
	//Criado por Rennan
	public boolean existeCpf(String cpf) {
		if (cpf.equals("") || cpf == null) {
			return false;
		}
		System.err.println("existe cpf");
		Usuario user = usuarioDAO.getUsuario(cpf);
		return user != null;		
	}
	
	public List<Avaliacao> getAvaliacoesDaTurma() {
		Turma turmaAtual = turmaBean.getTurma();
		if(turmaAtual == null)
			return null;
		else {
			avaliacoes = avaliacaoDAO.getAvaliacoes(turmaAtual);
			return avaliacoes;
		}
	}
	
	public List<Avaliacao> getAvaliacoesDoBanco() {
		AlunoTurma alunoTurmaAtual = alunoTurmaBean.getAlunoTurma();
		if(alunoTurmaAtual == null)
			return null;
		else {
			avaliacoes = avaliacaoDAO.getAvaliacoes(alunoTurmaAtual.getTurma());
			return avaliacoes;
		}
	}
	
	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}
	
	public float getNota(Avaliacao avaliacao) {
		AlunoTurma alunoTurmaAtual = alunoTurmaBean.getAlunoTurma();
		if((alunoTurmaAtual != null)&&(avaliacao != null)) {
			AlunoTurmaAvaliacao alunoTurmaAvaliacao = alunoTurmaAvaliacaoDAO.getAlunoTurmaAvaliacao(alunoTurmaAtual,avaliacao);
			if(alunoTurmaAvaliacao != null)
				return alunoTurmaAvaliacao.getNota();
			else
				return 0;
		}
		return 0;
	}
	
	public float getNota(AlunoTurma alunoTurma, Avaliacao avaliacao) {
		if((alunoTurma != null)&&(avaliacao != null)) {
		AlunoTurmaAvaliacao alunoTurmaAvaliacao = alunoTurmaAvaliacaoDAO.getAlunoTurmaAvaliacao(alunoTurma,avaliacao);
		if(alunoTurmaAvaliacao != null)
			return alunoTurmaAvaliacao.getNota();
		else
			return 0;
		}
		return 0;
	}
	
	public void persistePlanoDeCurso() {
		Turma turmaAtual = turmaBean.getTurma();
		if(turmaAtual != null)
			turmaDAO.atualiza(turmaAtual);
	}
	
	public AlunoTurmaAvaliacao getAlunoTurmaAvaliacao(AlunoTurma alunoTurma,Avaliacao avaliacao) {
		if((avaliacao != null)&&(alunoTurma != null)) {
			AlunoTurmaAvaliacao alunoTurmaAvaliacao = alunoTurmaAvaliacaoDAO.getAlunoTurmaAvaliacao(alunoTurma, avaliacao);
			if(alunoTurmaAvaliacao == null)
				alunoTurmaAvaliacao = alunoTurmaAvaliacaoDAO.cria(alunoTurma, avaliacao);
			alunoTurmaAvaliacoes.add(alunoTurmaAvaliacao);
			return alunoTurmaAvaliacao;
		}
		return new AlunoTurmaAvaliacao();
	}
	
	public List<AlunoTurma> getAlunoTurmaList() {
		AlunoTurma alunoTurmaAtual = alunoTurmaBean.getAlunoTurma();
		if(alunoTurmaAtual != null) {
			List<AlunoTurma> list = new ArrayList<AlunoTurma>();
			list.add(alunoTurmaAtual);
			return list;
		}
		else return null;
	}
	
	public void atualizarAvaliacao() {
		avaliacaoDAO.atualiza(this.avaliacao);
		this.avaliacao = new Avaliacao();
		criarOuEditar = true;
	}
	
	public void cancelaExclusao() {
		this.avaliacao = new Avaliacao();
	}
	
	public void excluiAvaliacao() {
		if(avaliacaoParaExclusao != null) {
			avaliacaoParaExclusao.setTurma(turmaBean.getTurma());
			avaliacaoDAO.remove(avaliacaoParaExclusao);
			avaliacaoParaExclusao = null;
		}
	}
	
	public void switchCalcMediaAuto() {
		Turma turmaAtual = turmaBean.getTurma();
		if (turmaAtual.isCalcularMediaAutomaticamente())
			turmaAtual.setCalcularMediaAutomaticamente(false);
		else turmaAtual.setCalcularMediaAutomaticamente(true);
	}
	
	public Avaliacao getAvaliacaoParaExclusao() {
		return avaliacaoParaExclusao;
	}

	public void setAvaliacaoParaExclusao(Avaliacao avaliacaoParaExclusao) {
		this.avaliacaoParaExclusao = avaliacaoParaExclusao;
	}

	public Periodo getPeriodoAtual() {
		if(globalBean != null)
			return globalBean.getGlobal().getPeriodoAtual();
		else
			return null;
	}

	public Global getGlobalDoBanco() {
		return globalDAO.getGlobal();
	}

	public void setTurma(Turma turma) {
		turmaBean.setTurma(turma);		
	}

	public Calendario getCalendarioDoBanco() {
		return calendarioDAO.getCalendario(getPeriodoAtual());
	}

	public Usuario getUsuario() {
		return usuarioBean.getUsuario();
	}

	public List<AlunoTurma> getAlunoTurmaDoBanco() {
		Usuario usuario = usuarioBean.getUsuario();
		if(usuario != null) {
			Aluno aluno = usuario.getAluno();
			if(aluno != null)
				return alunoTurmaDAO.getAlunoTurmas(aluno, getPeriodoAtual());
		}
		return null;
	}

	public void setAlunoTurma(AlunoTurma alunoTurma) {
		alunoTurmaBean.setAlunoTurma(alunoTurma);
	}
	
}
