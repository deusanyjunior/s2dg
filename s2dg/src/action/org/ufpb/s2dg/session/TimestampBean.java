package org.ufpb.s2dg.session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@Name("TimestampBean")
@Scope(ScopeType.SESSION)
@AutoCreate
public class TimestampBean {
	
	
	
	public String getHour(){
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	    return format.format(Calendar.getInstance().getTime()).toString();
	}
	
}
