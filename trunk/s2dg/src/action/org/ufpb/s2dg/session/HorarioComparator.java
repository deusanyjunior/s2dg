package org.ufpb.s2dg.session;

import java.util.Comparator;

import org.ufpb.s2dg.entity.Horario;

public class HorarioComparator implements Comparator<Horario> {

	public int compare(Horario h1, Horario h2) {
		int result1 = h1.getDia().compareTo(h2.getDia());
		if(result1 != 0)
			return result1;
		else {
			if(h1.getHoraInicio() < h2.getHoraInicio())
				return -1;
			else if(h1.getHoraInicio() > h2.getHoraInicio())
				return 1;
			else {
				if(h1.getHoraFim() < h2.getHoraFim())
					return -1;
				else if(h1.getHoraFim() > h2.getHoraFim())
					return 1;
				else
					return 0;
			}
		}
	}

}
