package model;

import javax.persistence.Entity;

/**
 * 
 * @author Florian Jungwirth, Michalea Murauer
 *
 */
@Entity
public class InterestAttribute extends CompetenceAndInterestAttribute {

	private static final long serialVersionUID = 1L;
	
	public InterestAttribute(){
		super();
	}
	
	public InterestAttribute(String name, String LOCdefinition) {
		super(name, LOCdefinition);
	}

}
