package org.ufpb.s2dg.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("MenuAction")
@Scope(ScopeType.SESSION)
@AutoCreate
public class MenuAction {
	
	//Menu Discente
	public int Id_Menu = 0;

	String[] lista_opcoes = {"/corpo_do_discente.xhtml", 
								"/configuracoes.xhtml",
							 	"/matricula.xhtml", 
							 	"/paginahistorico.xhtml", 
							 	"/paginahorario.xhtml"};
	//Menu Docente
	public int Id_Menu2 = 0;

	String[] lista_opcoes2 = {"/corpo_do_docente.xhtml", 
							 	"/configuracoes.xhtml"};
	
	//Menu Discente
	public String getOption() {
		return lista_opcoes[Id_Menu];
	}
	
	//Menu Docente
	public String getOption2() {
		return lista_opcoes2[Id_Menu2];
	}

	public void setId_Menu(int idMenu) {
		if(idMenu<2){
			this.Id_Menu = idMenu;
			this.Id_Menu2 = idMenu;
		}else {
			this.Id_Menu = idMenu;
			this.Id_Menu2 = 0;
		}
	}
	
}