package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * AvailabilityAttribute to save time slots when user is available - at the
 * moment only a DateAttribute, may be extended in future work
 * 
 * @author Florian Jungwirth, Michaela Murauer
 * 
 */
@Entity
public class AvailabilityAttribute extends DateAttribute {

	private static final long serialVersionUID = 1L;
	private Calendar endDate;

	public AvailabilityAttribute() {
		super();
	}

	public AvailabilityAttribute(String name, Calendar date, Calendar endDate) {
		super(name, date);
		this.endDate = endDate;
	}

	@Column(name = "ENDDATE", unique = false, nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	@Transient
	public String getFormatedDate() {
		if(date != null)
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date.getTime());
		else
			return "";
	}
	
	@Transient
	public String getFormatedEndDate() {
		if(endDate != null)
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(endDate.getTime());
		else
			return "";
	}
	
	@Override
	public String toString() {
		SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
		return format1.format(this.getDate().getTime());
	}

}
