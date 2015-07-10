package model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 
 * @author Florian Jungwirth, Michaela Murauer
 *
 */
@Entity
public class StringAttribute extends PersonalAttribute{

	private static final long serialVersionUID = 1L;
	private String value;

	public StringAttribute(){
		super();
	}
	
	public StringAttribute(String name, String value) {
		super(name);
		this.value = value;
	}

	@Column(name = "VALUE", unique = false, length = 120)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString(){
		return this.getValue();
	}
}
