package org.ufpb.s2dg.session.persistence;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.ufpb.s2dg.entity.AlunoTurma;
import org.ufpb.s2dg.entity.AlunoTurmaNota;
import org.ufpb.s2dg.entity.Nota;

@AutoCreate
@Name("alunoTurmaNotaDAO")
public class AlunoTurmaNotaDAO {

	@In
	EntityManager entityManager;

	public AlunoTurmaNota getAlunoTurmaNota(AlunoTurma alunoTurma, Nota nota) {
		try {
			if (alunoTurma != null) {
				Object result = entityManager.createQuery(
				"select alunoTurmaNota from AlunoTurmaNota as alunoTurmaNota where alunoTurmaNota.alunoTurma =:alunoTurma and alunoTurmaNota.nota =:nota")
				.setParameter("alunoTurma", alunoTurma)
				.setParameter("nota", nota)
				.getSingleResult();
				return (AlunoTurmaNota) result; 
			} else return null;
		} catch(NoResultException e) {
			return null;
		}
	}
	
}
