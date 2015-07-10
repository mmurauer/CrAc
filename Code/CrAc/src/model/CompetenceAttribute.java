package model;

import java.util.Calendar;

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
public class CompetenceAttribute extends CompetenceAndInterestAttribute {

	private static final long serialVersionUID = 1L;

	public CompetenceAttribute(){
		super();
	}
	
	public CompetenceAttribute(String name, String LOCdefinition) {
		super(name, LOCdefinition);
	}
	
	public CompetenceAttribute (String name, String LOCdefinition, Calendar date){
		super(name, LOCdefinition, date);
	}
	
	public CompetenceAttribute(String name, String LOCdefinition, Calendar date, Calendar expireDate) {
		super(name, LOCdefinition, date, expireDate);
	}
}
