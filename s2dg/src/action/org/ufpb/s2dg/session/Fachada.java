package org.ufpb.s2dg.session;

import java.util.Collections;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.model.CalendarDataModel;
import org.ufpb.s2dg.entity.Aluno;
import org.ufpb.s2dg.entity.AlunoTurma;
import org.ufpb.s2dg.entity.AlunoTurmaNota;
import org.ufpb.s2dg.entity.Calendario;
import org.ufpb.s2dg.entity.DisciplinaTurmas;
import org.ufpb.s2dg.entity.Global;
import org.ufpb.s2dg.entity.Nota;
import org.ufpb.s2dg.entity.Periodo;
import org.ufpb.s2dg.entity.Turma;
import org.ufpb.s2dg.entity.Usuario;
import org.ufpb.s2dg.session.persistence.AlunoTurmaDAO;
import org.ufpb.s2dg.session.persistence.AlunoTurmaNotaDAO;
import org.ufpb.s2dg.session.persistence.CalendarioDAO;
import org.ufpb.s2dg.session.persistence.GlobalDAO;
import org.ufpb.s2dg.session.persistence.NotaDAO;
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
	private NotaDAO notaDAO;
	@In
	private AlunoTurmaNotaDAO alunoTurmaNotaDAO;
	@In
	private GlobalDAO globalDAO;
	@In
	private CalendarioDAO calendarioDAO;
	
	private Usuario usuario;
	private AlunoTurma alunoTurmaAtual;
	private Turma turmaAtual;
	private List<AlunoTurma> alunoTurmas;
	private List<Nota> notas;
	private Calendario calendario;
	private Periodo periodoAtual;
	private Nota nota =  new Nota(); 

	public Periodo getPeriodoAtual() {
		return periodoAtual;
	}

	public void setPeriodoAtual(Periodo periodoAtual) {
		this.periodoAtual = periodoAtual;
	}

	public Nota getNota() {
		return nota;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
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
			List<AlunoTurma> alunoTurmas = alunoTurmaDAO.getAlunoTurmas(aluno);
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
		turmaDAO.atualiza(turmaAtual);
		for(int i = 0; i < alunoTurmas.size(); i++)
			alunoTurmaDAO.atualiza(alunoTurmas.get(i));
	}
	
	public void criarNota() {
		notaDAO.cria(nota, turmaAtual);
		nota = new Nota();
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
	
	public List<Nota> getNotasDaTurma() {
		if(turmaAtual == null)
			return null;
		else {
			notas = notaDAO.getNotas(turmaAtual);
			return notas;
		}
	}
	
	public List<Nota> getNotasDoBanco() {
		if(alunoTurmaAtual == null)
			return null;
		else {
			notas = notaDAO.getNotas(alunoTurmaAtual.getTurma());
			return notas;
		}
	}
	
	public List<Nota> getNotas() {
		return notas;
	}
	
	public float getValorDaNota(Nota nota) {
		AlunoTurmaNota alunoTurmaNota = alunoTurmaNotaDAO.getAlunoTurmaNota(alunoTurmaAtual,nota);
		if(alunoTurmaNota != null)
			return alunoTurmaNota.getValorDaNota();
		else
			return 0;
	}
	
	public float getValorDaNota(AlunoTurma alunoTurma, Nota nota) {
		AlunoTurmaNota alunoTurmaNota = alunoTurmaNotaDAO.getAlunoTurmaNota(alunoTurma,nota);
		if(alunoTurmaNota != null)
			return alunoTurmaNota.getValorDaNota();
		else
			return 0;
	}
	
	@Create
	public void initCalendario() {
		Global global = globalDAO.getGlobal();
		Periodo p = global.getPeriodoAtual();
		periodoAtual = new Periodo(p);
		calendario = calendarioDAO.getCalendario(periodoAtual);
	}
	
	public Calendario getCalendario() {
		return calendario;
	}
	
	public void persistePlanoDeCurso() {
		turmaDAO.atualiza(turmaAtual);
	}
	
}
