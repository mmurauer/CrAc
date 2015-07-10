package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 
 * @author Florian Jungwirth, Michaela Murauer
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class CompetenceAndInterestAttribute extends Attribute {
	
	private static final long serialVersionUID = 1L;
	private String LOCdefinition;
	private Calendar date;
	private Calendar expireDate;
	
	public CompetenceAndInterestAttribute(){
		super();
	}
	
	public CompetenceAndInterestAttribute(String name, String LOCdefinition) {
		super(name);
		this.LOCdefinition = LOCdefinition;
		this.date = Calendar.getInstance();
	}
	
	public CompetenceAndInterestAttribute(String name, String LOCdefinition, Calendar date) {
		super(name);
		this.LOCdefinition = LOCdefinition;
		this.date = date;
	}
	public CompetenceAndInterestAttribute(String name, String LOCdefinition, Calendar date, Calendar expireDate) {
		super(name);
		this.LOCdefinition = LOCdefinition;
		this.date = date;
		this.expireDate = expireDate;
	}

	@Column(name = "LOC_DEFINITION", unique = false, nullable = true, length = 500)
	public String getLOCdefinition() {
		return LOCdefinition;
	}

	public void setLOCdefinition(String LOCdefinition) {
		this.LOCdefinition = LOCdefinition;
	}
	
	@Column(name = "DATE", unique = false, nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
	
	@Column(name = "EXPIREDATE", unique = false, nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Calendar expireDate) {
		this.expireDate = expireDate;
	}

	@Transient
	public String getFormatedDate() {
		if(date != null)
			return new SimpleDateFormat("dd.MM.yyyy").format(date.getTime());
		else
			return "";
	}
	
	@Transient
	public String getFormatedExpireDate() {
		if(expireDate != null)
			return new SimpleDateFormat("dd.MM.yyyy").format(expireDate.getTime());
		else
			return "";
	}
	
	@Override
	public String toString(){
		SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
		return this.getLOCdefinition()+" Date: "+format1.format(this.getDate().getTime());
	}
}
