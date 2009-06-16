package org.ufpb.s2dg.session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.ufpb.s2dg.entity.Aluno;
import org.ufpb.s2dg.entity.AlunoTurma;
import org.ufpb.s2dg.entity.AlunoTurmaId;
import org.ufpb.s2dg.entity.Disciplina;
import org.ufpb.s2dg.entity.Turma;
import org.ufpb.s2dg.entity.Usuario;

@Name("alunoTurmaList")
public class AlunoTurmaList extends EntityQuery<AlunoTurma> {

	private static final String EJBQL = "select alunoTurma from AlunoTurma alunoTurma";

	private static final String[] RESTRICTIONS = { "lower(alunoTurma.id.matriculaAluno) like lower(concat(#{alunoTurmaList.alunoTurma.id.matriculaAluno},'%'))", };

	private AlunoTurma alunoTurma;

	private List<AlunoTurma> lista = new ArrayList<AlunoTurma>(); 
	
	
	public AlunoTurmaList() {
		alunoTurma = new AlunoTurma();
		alunoTurma.setId(new AlunoTurmaId());
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
		
		Disciplina disciplina = new Disciplina("1234", "Fulerage", 4);
		
		Turma turma = new Turma(1, disciplina, "1");
		
		Aluno aluno = new Aluno("1234567");
		
		Usuario user1 = new Usuario();
		user1.setAluno(aluno);
		user1.setNome("Fulano");
		
		aluno.setUsuario(user1);
		
		Aluno aluno2 = new Aluno("7654321");
		
		Usuario user2 = new Usuario();
		user1.setAluno(aluno2);
		user1.setNome("Fulaninho");
		
		aluno2.setUsuario(user2);
		
		AlunoTurmaId alunoTurmaId = new AlunoTurmaId(aluno.getMatricula(), turma.getId());
		
		AlunoTurmaId alunoTurmaId2 = new AlunoTurmaId(aluno2.getMatricula(), turma.getId());
		
		AlunoTurma alunoTurma1 = new AlunoTurma(alunoTurmaId, turma, aluno);

		AlunoTurma alunoTurma2 = new AlunoTurma(alunoTurmaId2, turma, aluno2);

		lista.add(alunoTurma1);
		lista.add(alunoTurma2);
	}
	
	

	public List<AlunoTurma> getLista() {
		return lista;
	}



	public void setLista(List<AlunoTurma> lista) {
		this.lista = lista;
	}



	public AlunoTurma getAlunoTurma() {
		return alunoTurma;
	}
}
