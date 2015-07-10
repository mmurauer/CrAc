package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Florian Jungwirth, Michaela Murauer
 * 
 */
@Entity
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;
	private int ID;
	private String street;
	private String streetnumber;
	private String city;
	private String postalcode;

	public Address() {
		super();
	}

	public Address(String street, String streetnumber, String city,
			String postalcode) {
		super();
		this.street = street;
		this.streetnumber = streetnumber;
		this.city = city;
		this.postalcode = postalcode;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	@Column(name = "STREET", unique = false, length = 120)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "NUMBER", unique = false, length = 120)
	public String getStreetnumber() {
		return streetnumber;
	}

	public void setStreetnumber(String streetnumber) {
		this.streetnumber = streetnumber;
	}

	@Column(name = "CITY", unique = false, length = 120)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "POSTALCODE", unique = false, length = 120)
	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	@Override
	public String toString() {
		return ((street != null) ? street : "") + " "
				+ ((streetnumber != null) ? streetnumber : "")
				+ ((street != null || streetnumber != null) ? ", " : "")
				+ ((postalcode != null) ? postalcode : "") + " "
				+ ((city != null) ? city : "");
	}

}
