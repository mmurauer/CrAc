package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Florian Jungwirth, Michaela Murauer
 *
 */
@Entity
public class DateAttribute extends PersonalAttribute {

	private static final long serialVersionUID = 1L;

	protected Calendar date;

	public DateAttribute(){
		super();
	}
	
	public DateAttribute(String name, Calendar date) {
		super(name);
		this.date = date;
	}

	@Column(name = "DATE", unique = false, nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
	
	@Override
	public String toString(){
		SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
		return format1.format(this.getDate().getTime());
	}
}
