package org.ufpb.s2dg.session;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.ufpb.s2dg.entity.Role;
import org.ufpb.s2dg.entity.Usuario;

@Name("authenticator")
public class Authenticator
{
    @Logger private Log log;

    @In 
    Identity identity;
    
    @In 
    Credentials credentials;
    
    @In(value="entityManager", create=true) 
    EntityManager em;

    @In(create=true)
    UsuarioHome usuarioHome;
    @In(create=true)
    AlunoTurmaList alunoTurmaList;
    
    public boolean authenticate()
    {
        log.info("authenticating {0}", credentials.getUsername());
        
        Usuario usuario = (Usuario) em.createQuery(
    	"select u from Usuario as u where u.cpf =:cpf and u.senha =:senha")
    	.setParameter("cpf", credentials.getUsername())
    	.setParameter("senha", credentials.getPassword())
    	.getSingleResult();
        
        if (usuario != null) {
            Set<Role> roles = usuario.getRoles();
            if(roles != null) {
            	Iterator<Role> rolesIt = roles.iterator();
            	while(rolesIt.hasNext()) {
            		Role role = rolesIt.next();
            		identity.addRole(role.getNome());
            	}
            }
        } else {
        	return false;
        }
        usuarioHome.setInstance(usuario);
        if(usuario.getAluno() != null)
        	alunoTurmaList.getAlunoTurma().getId().setMatriculaAluno(usuario.getAluno().getMatricula());
        return true;
    }

}
