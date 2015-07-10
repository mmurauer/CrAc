package model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * RangeAttribute used to save range of age required for a task f.e.
 * 
 * @author Florian Jungwirth, Michaela Murauer
 * 
 */
@Entity
public class RangeAttribute extends PersonalAttribute {

	private static final long serialVersionUID = 1L;
	private int from;
	private int to;

	public RangeAttribute() {
		super();
	}

	public RangeAttribute(String name, int from, int to) {
		super(name);
		this.from = from;
		this.to = to;
	}

	@Column(name = "FROM_VALUE", unique = false)
	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	@Column(name = "TO_VALUE", unique = false)
	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}
}
