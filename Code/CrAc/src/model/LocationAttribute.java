package model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 
 * @author Florian Jungwirth, Michaela Murauer
 *
 */
@Entity
public class LocationAttribute extends PersonalAttribute {
	
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private Address address;
	
	public LocationAttribute(){
		super();
	}
	public LocationAttribute(String name, int x, int y, Address address) {
		super(name);
		this.x = x;
		this.y = y;
		this.address = address;
	}
	
	public LocationAttribute(String name, int x, int y, String street, String streetnumber, String city, String postalcode) {
		super(name);
		this.x = x;
		this.y = y;
		this.address = new Address(street, streetnumber, city, postalcode);
	}

	@Column(name = "X", unique = false)
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	@Column(name = "Y", unique = false)
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@ManyToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "address")
	public Address getAddress(){
		return address;
	}
	
	public void setAddress(Address address){
		this.address = address;
	}
	
	@Override
	public String toString(){
		return this.address.toString();
	}
}
