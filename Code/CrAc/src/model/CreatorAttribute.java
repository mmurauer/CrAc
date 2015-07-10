package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Attribute for task creator - at the moment only a User, may be extended in
 * future work
 * 
 * @author Florian Jungwirth, Michaela Murauer
 * 
 */
@Entity
public class CreatorAttribute extends PersonalAttribute {

	private static final long serialVersionUID = 1L;
	private User creator;

	public CreatorAttribute() {
		super();
	}

	public CreatorAttribute(String name, User creator) {
		super(name);
		this.creator = creator;
	}

	@ManyToOne(targetEntity = User.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "CREATOR", nullable = false)
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}
}
