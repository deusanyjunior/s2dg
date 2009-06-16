package org.ufpb.s2dg.session;

import org.ufpb.s2dg.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("alunoTurmaList")
public class AlunoTurmaList extends EntityQuery<AlunoTurma> {

	private static final String EJBQL = "select alunoTurma from AlunoTurma alunoTurma";

	private static final String[] RESTRICTIONS = { "lower(alunoTurma.id.matriculaAluno) like lower(concat(#{alunoTurmaList.alunoTurma.id.matriculaAluno},'%'))", };

	private AlunoTurma alunoTurma;

	public AlunoTurmaList() { 
		alunoTurma = new AlunoTurma();
		alunoTurma.setId(new AlunoTurmaId());
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public AlunoTurma getAlunoTurma() {
		return alunoTurma;
	}
}
