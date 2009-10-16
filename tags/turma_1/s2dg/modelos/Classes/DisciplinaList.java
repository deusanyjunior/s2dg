package org.ufpb.s2dg.session;

import org.ufpb.s2dg.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("disciplinaList")
public class DisciplinaList extends EntityQuery<Disciplina> {

	private static final String EJBQL = "select disciplina from Disciplina disciplina";

	private static final String[] RESTRICTIONS = {
			"lower(disciplina.codigo) like lower(concat(#{disciplinaList.disciplina.codigo},'%'))",
			"lower(disciplina.nome) like lower(concat(#{disciplinaList.disciplina.nome},'%'))", };

	private Disciplina disciplina = new Disciplina();

	public DisciplinaList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}
}
