package org.ufpb.s2dg.session.persistence;

import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.ufpb.s2dg.entity.Avaliacao;
import org.ufpb.s2dg.entity.Turma;

@AutoCreate
@Name("avaliacaoDAO")
public class AvaliacaoDAO {

	@In
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Avaliacao> getAvaliacoes(Turma turma) {
		return (List<Avaliacao>) entityManager.createQuery(
    	"select avaliacao from Avaliacao as avaliacao where avaliacao.turma = :turma order by avaliacao.nome")
    	.setParameter("turma", turma)
    	.getResultList();
	}

	public void cria(Avaliacao avaliacao, Turma turma) {
		Avaliacao novaAvaliacao = new Avaliacao();
		novaAvaliacao.setNome(avaliacao.getNome());
		novaAvaliacao.setPeso(avaliacao.getPeso());
		novaAvaliacao.setTurma(turma);
		novaAvaliacao.setDataEvento(avaliacao.getDataEvento());
		entityManager.persist(novaAvaliacao);
		entityManager.flush();
	}

	public void atualiza(Avaliacao avaliacao) {
		if(avaliacao != null) {
			entityManager.merge(avaliacao);
			entityManager.flush();
		}
	}

	public void remove(Avaliacao avaliacao) {
		if(avaliacao != null) {
			Avaliacao avaliacaox = entityManager.find(Avaliacao.class,avaliacao.getId());
			avaliacaox.setTurma(avaliacao.getTurma());
			entityManager.remove(avaliacaox);
			entityManager.flush();
		}
	}

}
