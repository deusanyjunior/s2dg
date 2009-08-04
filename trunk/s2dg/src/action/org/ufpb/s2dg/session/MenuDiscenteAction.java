package org.ufpb.s2dg.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("MenuDiscenteAction")
@Scope(ScopeType.SESSION)
@AutoCreate
public class MenuDiscenteAction {

	public int Id_Menu = 0;

	String[] lista_opcoes = {"/corpo_turma_discente.xhtml", 
							 "/matricula.xhtml", 
							 "/paginahistorico.xhtml", 
							 "/paginahorario.xhtml", 
							 "/alterarSenha.xhtml"};
	
	public String getOption() {
		return lista_opcoes[Id_Menu];
	}

	public void setId_Menu(int idMenu) {
		this.Id_Menu = idMenu;
	}
	
}