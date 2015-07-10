package model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Evidence - used to proof all kind of Attributes
 * 
 * @author Florian Jungwirth, Michaela Murauer
 *
 */
@Entity
public class Evidence implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int ID;
	private String type;
	
	private Calendar date;
	private Calendar expireDate;
	
	private String value;
	private Issuer issuer;
	
	public Evidence(){
		super();
	}
	
	public Evidence(String type, Calendar date, Calendar expireDate,
			String value, Issuer issuer) {
		super();
		this.type = type;
		this.date = date;
		this.expireDate = expireDate;
		this.value = value;
		this.issuer = issuer;
		System.out.println(issuer.getID());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	public int getID(){
		return ID;
	}
	
	public void setID(int ID){
		this.ID = ID;
	}
	
	@Column(name = "TYPE", unique = false, length = 120)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "DATE", unique = false, nullable = true)
	@Temporal(TemporalType.DATE)
	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	@Column(name = "EXPIRE_DATE", unique = false, nullable = true)
	@Temporal(TemporalType.DATE)
	public Calendar getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Calendar expireDate) {
		this.expireDate = expireDate;
	}

	@Column(name = "VALUE", unique = false, nullable = true, length = 100)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@ManyToOne(targetEntity = Issuer.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "ISSUER", nullable = false)
	public Issuer getIssuer() {
		return issuer;
	}

	public void setIssuer(Issuer issuer) {
		this.issuer = issuer;
	}
	
	@Override
	public String toString(){
		return this.getType()+" Aussteller: "+this.getIssuer().toString();
	}
}
