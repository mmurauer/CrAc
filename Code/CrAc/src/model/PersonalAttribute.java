package model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * 
 * @author Florian Jungwirth, Michaela Murauer
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PersonalAttribute extends Attribute {
	
	private static final long serialVersionUID = 1L;

	public PersonalAttribute(){
		super();
	}
	
	public PersonalAttribute(String name) {
		super(name);
	}

}
