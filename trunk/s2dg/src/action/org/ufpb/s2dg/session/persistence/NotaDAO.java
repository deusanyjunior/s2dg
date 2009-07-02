package org.ufpb.s2dg.session.persistence;

import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.ufpb.s2dg.entity.Nota;
import org.ufpb.s2dg.entity.Turma;

@AutoCreate
@Name("notaDAO")
public class NotaDAO {

	@In
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Nota> getNotas(Turma turma) {
		return (List<Nota>) entityManager.createQuery(
    	"select nota from Nota as nota where nota.turma = :turma order by nota.nome")
    	.setParameter("turma", turma)
    	.getResultList();
	}

	public void cria(Nota nota, Turma turma) {
		Nota novaNota = new Nota();
		novaNota.setNome(nota.getNome());
		novaNota.setPeso(nota.getPeso());
		novaNota.setTurma(turma);
		entityManager.persist(novaNota);
	}

}
