package org.ufpb.s2dg.session;

import org.ufpb.s2dg.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("professorList")
public class ProfessorList extends EntityQuery<Professor> {

	private static final String EJBQL = "select professor from Professor professor";

	private static final String[] RESTRICTIONS = { "lower(professor.matricula) like lower(concat(#{professorList.professor.matricula},'%'))", };

	private Professor professor = new Professor();

	public ProfessorList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Professor getProfessor() {
		return professor;
	}
}
