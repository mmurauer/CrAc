package model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 
 * @author Florian Jungwirth, Michaela Murauer
 *
 */
@Entity
public class Organization extends Issuer{

	private static final long serialVersionUID = 1L;
	private String title;
	
	public Organization(){
		super();
	}
	
	public Organization(String title){
		this.title = title;
	}
	
	@Column(name = "TITLE", unique = false, nullable = false, length = 120)
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	@Override
	public String toString(){
		return this.title;
	}
}
