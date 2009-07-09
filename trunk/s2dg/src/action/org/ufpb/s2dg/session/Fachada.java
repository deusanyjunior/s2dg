package org.ufpb.s2dg.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.Aluno;
import org.ufpb.s2dg.entity.AlunoTurma;
import org.ufpb.s2dg.entity.AlunoTurmaAvaliacao;
import org.ufpb.s2dg.entity.Calendario;
import org.ufpb.s2dg.entity.DisciplinaTurmas;
import org.ufpb.s2dg.entity.Global;
import org.ufpb.s2dg.entity.Avaliacao;
import org.ufpb.s2dg.entity.Periodo;
import org.ufpb.s2dg.entity.Turma;
import org.ufpb.s2dg.entity.Usuario;
import org.ufpb.s2dg.session.persistence.AlunoTurmaDAO;
import org.ufpb.s2dg.session.persistence.AlunoTurmaAvaliacaoDAO;
import org.ufpb.s2dg.session.persistence.CalendarioDAO;
import org.ufpb.s2dg.session.persistence.GlobalDAO;
import org.ufpb.s2dg.session.persistence.AvaliacaoDAO;
import org.ufpb.s2dg.session.persistence.TurmaDAO;
import org.ufpb.s2dg.session.persistence.UsuarioDAO;

@Name("fachada")
@Scope(ScopeType.SESSION)
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
	
	private Usuario usuario;
	private AlunoTurma alunoTurmaAtual;
	private Turma turmaAtual;
	private List<AlunoTurma> alunoTurmas;
	private List<Avaliacao> avaliacoes;
	private Calendario calendario;
	private Periodo periodoAtual;
	private Avaliacao avaliacao = new Avaliacao();
	private List<AlunoTurmaAvaliacao> alunoTurmaAvaliacoes;
	private boolean criarOuEditar;
	private Avaliacao avaliacaoParaExclusao;
	
	public boolean isCriarOuEditar() {
		return criarOuEditar;
	}

	public void setCriarOuEditar(boolean criarOuEditar) {
		this.criarOuEditar = criarOuEditar;
	}

	public Periodo getPeriodoAtual() {
		return periodoAtual;
	}

	public void setPeriodoAtual(Periodo periodoAtual) {
		this.periodoAtual = periodoAtual;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioDoBanco(String username, String password) {
		return usuarioDAO.getUsuario(username,password);
	}
	
	public List<AlunoTurma> getAlunoTurmasDoBanco() {
		if (usuario == null)
			return null;
		Aluno aluno = usuario.getAluno();
		if (aluno != null) {
			List<AlunoTurma> alunoTurmas = alunoTurmaDAO.getAlunoTurmas(aluno, periodoAtual);
			if (alunoTurmas.size() > 0) {
				alunoTurmaAtual = alunoTurmas.get(0);
				return alunoTurmas;
			}
		}
		return null;
	}

	public AlunoTurma getAlunoTurmaAtual() {
		return alunoTurmaAtual;
	}

	public void setAlunoTurmaAtual(AlunoTurma alunoTurmaAtual) {
		this.alunoTurmaAtual = alunoTurmaAtual;
	}
	
	public String getNomeDoProfessorAtual() {
		if (alunoTurmaAtual == null)
			return null;
		return usuarioDAO.getUsuarioProfessor(alunoTurmaAtual.getTurma().getProfessor().getMatricula()).getNome();
	}
	
	public List<DisciplinaTurmas> getTurmasPorDisciplina() {
		List<Turma> turmas = turmaDAO.getTurmas(usuario.getProfessor(),periodoAtual);
		if(turmas != null) {
			List<DisciplinaTurmas> disciplinaTurmas = DisciplinaTurmas.geraTurmasPorDisciplina(turmas);
			if(disciplinaTurmas != null) {
				List<Turma> dturmas = disciplinaTurmas.get(0).getTurmas();
				if(dturmas != null)
					turmaAtual = dturmas.get(0);
			}
			return disciplinaTurmas;
		}
		return null;
	}

	public Turma getTurmaAtual() {
		return turmaAtual;
	}

	public void setTurmaAtual(Turma turmaAtual) {
		this.turmaAtual = turmaAtual;
		avaliacao = new Avaliacao();
		criarOuEditar = true;
	}
	
	public List<AlunoTurma> getAlunoTurmas() {
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
		if(turmaAtual == null)
			return null;
		else {
			avaliacoes = avaliacaoDAO.getAvaliacoes(turmaAtual);
			return avaliacoes;
		}
	}
	
	public List<Avaliacao> getAvaliacoesDoBanco() {
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
	
	@Create
	public void initCalendario() {
		Global global = globalDAO.getGlobal();
		if(global != null) {
			Periodo p = global.getPeriodoAtual();
			if(p != null) {
				periodoAtual = new Periodo(p);
				calendario = calendarioDAO.getCalendario(periodoAtual);
			}
		}
		criarOuEditar = true;
	}
	
	public Calendario getCalendario() {
		return calendario;
	}
	
	public void persistePlanoDeCurso() {
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
			avaliacaoParaExclusao.setTurma(turmaAtual);
			avaliacaoDAO.remove(avaliacaoParaExclusao);
			avaliacaoParaExclusao = null;
		}
	}
	
	public void switchCalcMediaAuto() {
		if (this.turmaAtual.isCalcularMediaAutomaticamente())
			this.turmaAtual.setCalcularMediaAutomaticamente(false);
		else this.turmaAtual.setCalcularMediaAutomaticamente(true);
	}
	public Avaliacao getAvaliacaoParaExclusao() {
		return avaliacaoParaExclusao;
	}

	public void setAvaliacaoParaExclusao(Avaliacao avaliacaoParaExclusao) {
		this.avaliacaoParaExclusao = avaliacaoParaExclusao;
	}
	
}
