package org.ufpb.s2dg.session;

import java.util.Comparator;

import org.ufpb.s2dg.entity.AlunoTurma;

public class AlunoTurmaComparator implements Comparator<AlunoTurma> {

	public int compare(AlunoTurma o1, AlunoTurma o2) {
		String nome1 = o1.getAluno().getUsuario().getNome();
		String nome2 = o2.getAluno().getUsuario().getNome();
		return nome1.compareTo(nome2); 
	}

}
