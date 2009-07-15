package org.ufpb.s2dg.session.persistence;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.ufpb.s2dg.entity.Usuario;

@AutoCreate
@Name("usuarioDAO")
public class UsuarioDAO {

	@In
	EntityManager entityManager;
	
	public Usuario getUsuario(String username, String password) {
		return (Usuario) entityManager.createQuery(
    	"select u from Usuario as u where u.cpf =:cpf and u.senha =:senha")
    	.setParameter("cpf", username)
    	.setParameter("senha", password)
    	.getSingleResult();
	}
	
	//Criado por Julio e Rennan
	public Usuario getUsuario(String username){
		Object result = entityManager.createQuery(
				    	"select u from Usuario as u where u.cpf =:cpf")
				    	.setParameter("cpf", username)
				    	.getSingleResult();
		if (result == null)
			return null;
		return (Usuario) result;
	}

	public Usuario getUsuarioProfessor(String matricula) {
		return (Usuario) entityManager.createQuery(
    	"select u from Usuario as u where u.professor.matricula =:matricula")
    	.setParameter("matricula", matricula)
    	.getSingleResult();
	}
	
	public Usuario getUsuarioAluno(String matricula) {
		return (Usuario) entityManager.createQuery(
    	"select u from Usuario as u where u.aluno.matricula =:matricula")
    	.setParameter("matricula", matricula)
    	.getSingleResult();
	}
	
	//Criado por Julio
	public void alteraSenha(String CPF, String senhaAtual, String novaSenha){
		entityManager.createQuery("update Usuario Set senha = :senha where cpf = :cpf")
		.setParameter("cpf", CPF)
		.setParameter("senha", novaSenha).executeUpdate();
	}
}
