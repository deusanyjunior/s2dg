package org.ufpb.s2dg.session;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.richfaces.model.CalendarDataModelItem;

public class ItemDeCalendario implements CalendarDataModelItem, Serializable {
	private Object data;
	private int day;
	private static final long serialVersionUID = 1L;
	
	public ItemDeCalendario(int dia) {
		data = "";
		day = dia;
	}
	
	public ItemDeCalendario(String eventos, int dia) {
		data = eventos;
		day = dia;
	}
	
	public Object getData() {
		// TODO Auto-generated method stub
		return data;
	}
	
	public void setData(String informacao) {
		data = informacao; 
	}

	public int getDay() {
		// TODO Auto-generated method stub
		return day;
	}
		
	public void setDay(int x) {
		day = x;
	}

	public String getStyleClass() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getToolTip() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasToolTip() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
