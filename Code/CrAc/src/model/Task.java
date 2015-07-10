package model;

import inloc.LOCdefinition;
import inloc.LOCrel;
import inloc.LOCscheme;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 
 * @author Florian Jungwirth, Michaela Murauer
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Task.getAll", query = "SELECT t FROM Task t") })
public class Task extends Issuer implements Visualizable {

	private static final long serialVersionUID = 1L;
	private List<Attribute> attributes = new ArrayList<>();
	private List<Task> subTasks = new ArrayList<>();

	public Task() {
		super();
	}

	public Task(List<Attribute> attributes, List<Task> subTasks) {
		this.attributes = attributes;
		this.subTasks = subTasks;
	}

	@OneToMany(targetEntity = Attribute.class, cascade = CascadeType.ALL)
	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(Attribute attribute) {
		attributes.add(attribute);
	}

	@OneToMany(targetEntity = Task.class, cascade = CascadeType.ALL)
	public List<Task> getSubTasks() {
		return subTasks;
	}

	public void setSubTasks(List<Task> subTasks) {
		this.subTasks = subTasks;
	}

	public void addSubTask(Task subTask) {
		subTasks.add(subTask);
	}

	@Transient
	public String getName() {
		for (Attribute attribute : attributes) {
			if (attribute.getName().equals("name"))
				return ((StringAttribute) attribute).getValue();
		}
		return "";
	}

	@Transient
	public String getDescription() {
		for (Attribute attribute : attributes) {
			if (attribute.getName().equals("description"))
				return ((StringAttribute) attribute).getValue();
		}
		return "";
	}

	@Transient
	public List<CompetenceAndInterestAttribute> getCompetencies() {
		List<CompetenceAndInterestAttribute> competencies = new ArrayList<>();
		for (Attribute attribute : attributes) {
			if (attribute.getClass().equals(CompetenceAttribute.class))
				competencies.add((CompetenceAttribute) attribute);
		}
		return competencies;
	}

	@Transient
	public List<CompetenceAndInterestAttribute> getInterests() {
		List<CompetenceAndInterestAttribute> interests = new ArrayList<>();
		for (Attribute attribute : attributes) {
			if (attribute.getClass().equals(InterestAttribute.class))
				interests.add((InterestAttribute) attribute);
		}
		return interests;
	}

	@Transient
	public boolean hasCompetence(LOCdefinition locDefinition, Calendar date) {
		for (CompetenceAndInterestAttribute competence : this.getCompetencies()) {
			if (competence.getLOCdefinition().equals(locDefinition.getId())) {
				return true;
			}
		}
		return false;
	}

	@Transient
	public boolean hasInterest(LOCdefinition locDefinition) {
		for (CompetenceAndInterestAttribute interest : this.getInterests()) {
			if (interest.getLOCdefinition().equals(locDefinition.getId())) {
				return true;
			}
		}
		return false;
	}

	@Transient
	public LocationAttribute getLocation() {
		for (Attribute attribute : attributes) {
			if (attribute.getClass().equals(LocationAttribute.class))
				return ((LocationAttribute) attribute);
		}
		return null;
	}

	/**
	 * check if competence/interest/noninterest is required in task - used
	 * for graph vizualisation
	 * 
	 * @param locDefinition
	 * @param type
	 * @return
	 */
	@Transient
	public boolean containsAttributeForBubbleGraph(LOCdefinition locDefinition,
			String type) {
		boolean hasAttr = false;
		switch (type) {
		case "competence":
		case "competence_zoom":
			hasAttr = this.hasCompetence(locDefinition, Calendar.getInstance());
			break;
		case "interest":
		case "interest_zoom":
			hasAttr = this.hasInterest(locDefinition);
			break;
		}

		if (hasAttr)
			return true;

		List<LOCrel> childs = locDefinition.getChildren(LOCscheme.hasPart);
		for (LOCrel rel : childs) {
			hasAttr = containsAttributeForBubbleGraph(
					(LOCdefinition) rel.getLocType(), type);
			if (hasAttr)
				return true;
		}

		childs = locDefinition.getChildren(LOCscheme.hasDefinedLevel);
		for (LOCrel rel : childs) {
			hasAttr = containsAttributeForBubbleGraph(
					(LOCdefinition) rel.getLocType(), type);
			if (hasAttr)
				return true;
		}

		childs = locDefinition.getChildren(LOCscheme.hasExample);
		for (LOCrel rel : childs) {
			hasAttr = containsAttributeForBubbleGraph(
					(LOCdefinition) rel.getLocType(), type);
			if (hasAttr)
				return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
