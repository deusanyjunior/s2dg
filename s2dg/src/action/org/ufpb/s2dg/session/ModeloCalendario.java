package org.ufpb.s2dg.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import org.jboss.seam.annotations.Name;
import org.richfaces.model.CalendarDataModel;
import org.richfaces.model.CalendarDataModelItem;
import org.ufpb.s2dg.entity.DataEvento;
import org.ufpb.s2dg.session.persistence.DataEventoDAO;

@Name("modeloCalendario")
public class ModeloCalendario implements CalendarDataModel, Serializable {
	ItemDeCalendario[] itens;
	private static final long serialVersionUID = 1L;
	
	public CalendarDataModelItem[] getData(Date[] datas) {
		if (datas == null) return null;
		
		itens = new ItemDeCalendario[datas.length];
		Calendar c = Calendar.getInstance();
		c.setTime(datas[0]);
		ArrayList<DataEvento> datasEEventos = new DataEventoDAO().getDataEvento(c.get(Calendar.MONTH) + 1, 
				c.get(Calendar.YEAR));
		String eventos = "";
		
		for (int i = 0; i < datas.length; i++) {
			if (datas[i] != null) {
				for (int j = 0; j < datasEEventos.size(); j++) {
					if (datas[i].equals(datasEEventos.get(j).getData())) {
						eventos += datasEEventos.remove(j--).getEvento() + "\n";
					}
				}
				c.setTime(datas[i]);
				
				if (eventos.equals("")) {
					itens[i] = new ItemDeCalendario(c.get(Calendar.DAY_OF_MONTH));
				}
				else {
					itens[i] = new ItemDeCalendario(eventos, c.get(Calendar.DAY_OF_MONTH));
					eventos = "";
				}
			}
		}
		/*System.out.println(c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + 
					c.get(Calendar.YEAR));*/
		return itens;
	}

	public Object getToolTip(Date arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*private ItemDeCalendario criarItemDeCalendario(Date data) {
		ItemDeCalendario item = new ItemDeCalendario();
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		int dia = c.get(Calendar.DAY_OF_MONTH);
		item.setDay(dia);
		
		//puxar do banco
		ArrayList<DataEvento> datasEEventos = new DataEventoDAO().getDataEvento(c.get(Calendar.MONTH) + 1, 
				c.get(Calendar.YEAR) + 1969);
		String eventos = "";
        
        for (DataEvento de : datasEEventos) {
        	eventos += de.getEvento() + "\n"; //dado puxado do banco
        }
        item.setData(eventos);
        return item;
	}*/
}
