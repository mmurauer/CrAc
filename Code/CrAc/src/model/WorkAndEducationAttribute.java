package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Work or education entry in user timeline
 * @author ella
 *
 */
@Entity
public class WorkAndEducationAttribute extends Attribute {

	private static final long serialVersionUID = 1L;
	
	private Calendar begin;
	private Calendar end;

	private String description;
	private Organization organization = null;

	public WorkAndEducationAttribute() {
		super();
	}

	public WorkAndEducationAttribute(String name, Calendar begin,
			Calendar end, String description, Organization organization) {
		super(name);
		this.begin = begin;
		this.end = end;
		this.description = description;
		this.organization = organization;
	}

	public WorkAndEducationAttribute(String name, Calendar begin,
			Calendar end, String description) {
		super(name);
		this.begin = begin;
		this.end = end;
		this.description = description;
	}

	@Column(name = "BEGIN", unique = false, nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getBegin() {
		return begin;
	}

	public void setBegin(Calendar begin) {
		this.begin = begin;
	}

	@Column(name = "END", unique = false, nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getEnd() {
		return end;
	}

	public void setEnd(Calendar end) {
		this.end = end;
	}

	@Column(name = "DESCRIPTION", unique = false, nullable = true, length = 120)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne(targetEntity = Organization.class, optional = true, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Override
	public String toString() {
		SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
		return ((this.getBegin() != null)?format1.format(this.getBegin().getTime()) + "-":"")
				+ ((this.getEnd() != null) ? format1.format(this.getEnd().getTime()) : "laufend") + " "
				+ ((this.getOrganization()!=null)?this.getOrganization().getTitle():"");
	}
}
