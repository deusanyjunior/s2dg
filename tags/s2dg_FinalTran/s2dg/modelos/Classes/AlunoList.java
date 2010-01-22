package org.ufpb.s2dg.session;

import org.ufpb.s2dg.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("alunoList")
public class AlunoList extends EntityQuery<Aluno> {

	private static final String EJBQL = "select aluno from Aluno aluno";

	private static final String[] RESTRICTIONS = { "lower(aluno.matricula) like lower(concat(#{alunoList.aluno.matricula},'%'))", };

	private Aluno aluno = new Aluno();

	public AlunoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Aluno getAluno() {
		return aluno;
	}
}
