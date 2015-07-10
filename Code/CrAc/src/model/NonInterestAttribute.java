package model;

import javax.persistence.Entity;

/**
 * 
 * @author Florian Jungwirth, Michaela Murauer
 *
 */
@Entity
public class NonInterestAttribute extends InterestAttribute {
	private static final long serialVersionUID = 1L;

	public NonInterestAttribute(){
		super();
	}
	
	public NonInterestAttribute(String name, String LOCdefinition) {
		super(name, LOCdefinition);
	}
}
