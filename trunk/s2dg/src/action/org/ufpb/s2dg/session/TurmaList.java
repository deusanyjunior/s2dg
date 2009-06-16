package org.ufpb.s2dg.session;

import org.ufpb.s2dg.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("turmaList")
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
}
