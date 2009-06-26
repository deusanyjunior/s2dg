package org.ufpb.s2dg.session;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.AlunoTurma;
import org.ufpb.s2dg.entity.AlunoTurmaNota;
import org.ufpb.s2dg.entity.DisciplinaTurmas;
import org.ufpb.s2dg.entity.Nota;
import org.ufpb.s2dg.entity.Turma;
import org.ufpb.s2dg.entity.Usuario;

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
	private NotaDAO notaDAO;
	@In
	private AlunoTurmaNotaDAO alunoTurmaNotaDAO;
	
	private Usuario usuario;
	private AlunoTurma alunoTurmaAtual;
	private Turma turmaAtual;
	private List<AlunoTurma> alunoTurmas;
	private List<Nota> notas;

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
		List<AlunoTurma> alunoTurmas = alunoTurmaDAO.getAlunoTurmas(usuario.getAluno());
		alunoTurmaAtual = alunoTurmas.get(0);
		return alunoTurmas;
	}

	public AlunoTurma getAlunoTurmaAtual() {
		return alunoTurmaAtual;
	}

	public void setAlunoTurmaAtual(AlunoTurma alunoTurmaAtual) {
		this.alunoTurmaAtual = alunoTurmaAtual;
	}
	
	public String getNomeDoProfessorAtual() {
		return usuarioDAO.getUsuarioProfessor(alunoTurmaAtual.getTurma().getProfessor().getMatricula()).getNome();
	}
	
	public List<DisciplinaTurmas> getTurmasPorDisciplina() {
		List<Turma> turmas = turmaDAO.getTurmas(usuario.getProfessor());
		List<DisciplinaTurmas> disciplinaTurmas = DisciplinaTurmas.geraTurmasPorDisciplina(turmas);
		if(disciplinaTurmas != null) {
			List<Turma> dturmas = disciplinaTurmas.get(0).getTurmas();
			if(dturmas != null)
				turmaAtual = dturmas.get(0);
		}
		return disciplinaTurmas;
	}

	public Turma getTurmaAtual() {
		return turmaAtual;
	}

	public void setTurmaAtual(Turma turmaAtual) {
		this.turmaAtual = turmaAtual;
	}
	
	public List<AlunoTurma> getAlunoTurmas() {
		alunoTurmas = alunoTurmaDAO.getAlunoTurmas(turmaAtual);
		for(int i = 0; i < alunoTurmas.size(); i++) {
			alunoTurmas.get(i).getAluno().setUsuario(getUsuarioAluno(alunoTurmas.get(i).getAluno().getMatricula()));
		}
		Collections.sort(alunoTurmas,new AlunoTurmaComparator());
		return alunoTurmas;
	}
	
	public Usuario getUsuarioAluno(String matricula) {
		return usuarioDAO.getUsuarioAluno(matricula);
	}
	
	public void persisteAlunoTurmas() {
		for(int i = 0; i < alunoTurmas.size(); i++)
			alunoTurmaDAO.atualiza(alunoTurmas.get(i));
	}
	
	//Criado por Julio e Rennan
	public String getEmail(String username) {
		return usuarioDAO.getUsuario(username).getEmail();
	}
	
	public List<Nota> getNotasDoBanco() {
		notas = notaDAO.getNotas(alunoTurmaAtual.getTurma());
		return notas;
	}
	
	public List<Nota> getNotas() {
		return notas;
	}
	
	public float getValorDaNota(Nota nota) {
		try {
			AlunoTurmaNota alunoTurmaNota = alunoTurmaNotaDAO.getAlunoTurmaNota(alunoTurmaAtual,nota);
			if(alunoTurmaNota != null)
				return alunoTurmaNota.getValorDaNota();
			else
				return 0;
		}
		catch (NoResultException e) {
			return 0;
		}
	}
	
}
