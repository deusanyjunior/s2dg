package org.ufpb.s2dg.session;

import org.ufpb.s2dg.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("periodoList")
public class PeriodoList extends EntityQuery<Periodo> {

	private static final String EJBQL = "select periodo from Periodo periodo";

	private static final String[] RESTRICTIONS = { "lower(periodo.ano) like lower(concat(#{periodoList.periodo.ano},'%'))", };

	private Periodo periodo = new Periodo();

	public PeriodoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Periodo getPeriodo() {
		return periodo;
	}
}
