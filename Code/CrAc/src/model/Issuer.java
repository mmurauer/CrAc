package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Issuer for Evidence can be user/task or simple issuer
 * 
 * @author Florian Jungwirth, Michaela Murauer
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Issuer implements Serializable {

	private static final long serialVersionUID = 1L;
	private int ID;

	public Issuer() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
}
