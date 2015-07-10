package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

/**
 * 
 * @author Florian Jungwirth, Michaela Murauer
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Attribute implements Serializable {

	private static final long serialVersionUID = 1L;
	private int ID;
	private String name;
	private List<Evidence> evidencies = new ArrayList<>();
	
	public Attribute(){
		super();
	}
	
	public Attribute(String name) {
		this.name = name;
	}
	
	public Attribute(String name, List<Evidence> evidencies) {
		this.name = name;
		this.evidencies = evidencies;
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
	
	@Column(name = "NAME", unique = false, nullable = true, length = 120)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//All important: Evidence can not exist without any attribute
	@ManyToMany(targetEntity = Evidence.class, cascade = CascadeType.ALL)
	public List<Evidence> getEvidencies() {
		return evidencies;
	}

	public void setEvidencies(List<Evidence> evidencies) {
		this.evidencies = evidencies;
	}
	
	public void addEvidence(Evidence evidence){
		evidencies.add(evidence);
	}
	
	@Override
	public String toString(){
		return "attribute";
	}
}
