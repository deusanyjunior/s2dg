package org.ufpb.s2dg.session;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;
import org.ufpb.s2dg.entity.Role;
import org.ufpb.s2dg.entity.Usuario;

@Name("authenticator")
public class Authenticator
{
    @Logger 
    Log log;
    
    @In(value="entityManager", create=true)
    private EntityManager em;
    
    @In(required=true)
    private String cpfToTest;
    
    @In(required=true)
    private String senhaToTest;
    
    @In 
    Identity identity;
    
    public boolean authenticate()
    {
        log.info("authenticating #0", identity.getUsername());
        
        Usuario usuario = (Usuario) em.createQuery(
        	"select u from Usuario as u where u.cpf =:cpf and u.senha =:senha")
        	.setParameter("cpf", cpfToTest)
        	.setParameter("senha", senhaToTest)
        	.getSingleResult();
        
        
        
        Set<Role> roles = usuario.getRoles();
        if(roles != null) {
        	Iterator<Role> rolesIt = roles.iterator();
        	while(rolesIt.hasNext()) {
        		Role role = rolesIt.next();
        		identity.addRole(role.getNome());
        	}
        }
        return true;
    }

	public String getCpfToTest() {
		return cpfToTest;
	}

	public void setCpfToTest(String cpfToTest) {
		this.cpfToTest = cpfToTest;
	}

	public String getSenhaToTest() {
		return senhaToTest;
	}

	public void setSenhaToTest(String senhaToTest) {
		this.senhaToTest = senhaToTest;
	}

}
