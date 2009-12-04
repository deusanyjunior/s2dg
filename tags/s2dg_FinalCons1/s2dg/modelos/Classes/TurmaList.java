package org.ufpb.s2dg.session;

import java.util.Arrays;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityQuery;
import org.ufpb.s2dg.entity.Professor;
import org.ufpb.s2dg.entity.Turma;

@Name("turmaList")
@Scope(ScopeType.SESSION)
public class TurmaList extends EntityQuery<Turma> {

	private static final String EJBQL = "select turma from Turma turma";

	private static final String[] RESTRICTIONS = { "lower(turma.numero) like lower(concat(#{turmaList.turma.numero},'%'))", };

	private Turma turma = new Turma();

	public TurmaList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Turma getTurma() {
		return turma;
	}
	
	public List<Turma> getTurmas(Professor professor) {
		String ejbql = "select turma from Turma turma where turma.professor = :professorn";
		return (List<Turma>)getEntityManager().createQuery(ejbql).setParameter("professorn", professor).getResultList();
	}
}
