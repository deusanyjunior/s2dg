package org.ufpb.s2dg.session.persistence;

import java.util.Comparator;

import org.ufpb.s2dg.entity.Avaliacao;
import org.ufpb.s2dg.entity.DataEvento;

public class AvaliacaoComparator implements Comparator<Avaliacao> {

	public int compare(Avaliacao arg0, Avaliacao arg1) {
		DataEvento d1 = arg0.getDataEvento();
		DataEvento d2 = arg1.getDataEvento();
		if((d1 != null)&&(d2 != null))
			return d1.getData().compareTo(d2.getData());
		else {
			if(d1 == null)
				return 1;
			else if(d2 == null)
				return -1;
			else {
				return arg0.getNome().compareTo(arg1.getNome());
			}
		}
	}

}