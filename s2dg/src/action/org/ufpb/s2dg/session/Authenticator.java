package org.ufpb.s2dg.session;

import java.util.Iterator;
import java.util.Set;

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

    @In
    Fachada fachada;
    
    @In
    UsuarioBean usuarioBean;
    
    public boolean authenticate()
    {
        log.info("authenticating {0}", credentials.getUsername().replaceAll("[.]", "").replaceAll("[-]", ""));
        
        /* Inserir Método de Criptografia de Senha */
        
        Usuario usuario = fachada.getUsuarioDoBanco(credentials.getUsername().replaceAll("[.]", "").replaceAll("[-]", ""),credentials.getPassword()); 
        
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
        
        usuarioBean.setUsuario(usuario);
        
        return true;
    }

}
