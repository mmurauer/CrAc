package model;

import inloc.LOCdefinition;
import inloc.LOCrel;
import inloc.LOCscheme;
import inloc.LOCtypeLOC;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * 
 * @author Florian Jungwirth, Michaela Murauer
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "User.getAll", query = "SELECT u FROM User u") })
public class User extends Issuer implements Visualizable {

	private static final long serialVersionUID = 1L;
	private List<Attribute> attributes = new ArrayList<>();

	private List<User> likesToWorkWith = new ArrayList<>();
	private List<User> likesNotToWorkWith = new ArrayList<>();
	private List<User> hasWorkedWith = new ArrayList<>();

	public User() {
		super();
	}

	public User(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public User(List<Attribute> attributes, List<User> likesToWorkWith,
			List<User> likesNotToWorkWith, List<User> hasWorkedWith) {
		this.attributes = attributes;
		this.likesToWorkWith = likesToWorkWith;
		this.likesNotToWorkWith = likesNotToWorkWith;
		this.hasWorkedWith = hasWorkedWith;
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

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "user_likestoworkwith", joinColumns = @JoinColumn(name = "user1", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "user2", referencedColumnName = "ID"))
	public List<User> getLikesToWorkWith() {
		return likesToWorkWith;
	}

	public void setLikesToWorkWith(List<User> likesToWorkWith) {
		this.likesToWorkWith = likesToWorkWith;
	}

	public void addLikesToWorkWith(User user) {
		if (!likesToWorkWith.contains(user))
			likesToWorkWith.add(user);
	}

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "user_likesnottoworkwith", joinColumns = @JoinColumn(name = "user1", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "user2", referencedColumnName = "ID"))
	public List<User> getLikesNotToWorkWith() {
		return likesNotToWorkWith;
	}

	public void setLikesNotToWorkWith(List<User> likesNotToWorkWith) {
		this.likesNotToWorkWith = likesNotToWorkWith;
	}

	public void addLikesNotToWorkWith(User user) {
		if (!likesNotToWorkWith.contains(user))
			likesNotToWorkWith.add(user);
	}

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "user_hasworkedwith", joinColumns = @JoinColumn(name = "user1", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "user2", referencedColumnName = "ID"))
	public List<User> getHasWorkedWith() {
		return hasWorkedWith;
	}

	public void setHasWorkedWith(List<User> hasWorkedWith) {
		this.hasWorkedWith = hasWorkedWith;
	}

	public void addHasWorkedWith(User user) {
		if (!hasWorkedWith.contains(user)) {
			hasWorkedWith.add(user);

			// if user a has worked with user b, user b has also worked with
			// user a
			if (!user.getHasWorkedWith().contains(this))
				user.addHasWorkedWith(this);
		}
	}

	/**
	 * not dynamically - could be done like displaying all personal attributes
	 * available in user profile - may be done in future work
	 * 
	 * @return
	 */
	@Transient
	public String getFirstname() {
		for (Attribute attribute : attributes) {
			if (attribute.getName().equals("firstname"))
				return ((StringAttribute) attribute).getValue();
		}
		return "";
	}

	/**
	 * not dynamically - could be done like displaying all personal attributes
	 * available in user profile - may be done in future work
	 * 
	 * @return
	 */
	@Transient
	public String getLastname() {
		for (Attribute attribute : attributes) {
			if (attribute.getName().equals("lastname"))
				return ((StringAttribute) attribute).getValue();
		}
		return "";
	}

	/**
	 * not dynamically - could be done like displaying all personal attributes
	 * available in user profile - may be done in future work
	 * 
	 * @return
	 */
	@Transient
	public String getName() {
		return getFirstname() + " " + getLastname();
	}

	/**
	 * not dynamically - could be done like displaying all personal attributes
	 * available in user profile - may be done in future work
	 * 
	 * @return
	 */
	@Transient
	public int getAge() {
		for (Attribute attribute : attributes) {
			Calendar dateOfBirth;
			if (attribute.getName().equals("birthday")) {
				dateOfBirth = ((DateAttribute) attribute).getDate();
				Calendar currentTime = Calendar.getInstance();
				int age = currentTime.get(Calendar.YEAR)
						- dateOfBirth.get(Calendar.YEAR);
				if (currentTime.get(Calendar.DAY_OF_YEAR) < dateOfBirth
						.get(Calendar.DAY_OF_YEAR))
					age--;

				return age;
			}
		}
		return -1;
	}
	/**
	 * not dynamically - could be done like displaying all personal attributes
	 * available in user profile - may be done in future work
	 * 
	 * @return
	 */
	@Transient
	public String getBirthday() {
		for (Attribute attribute : attributes) {
			Calendar dateOfBirth;
			if (attribute.getName().equals("birthday")) {
				dateOfBirth = ((DateAttribute) attribute).getDate();
				return new SimpleDateFormat("dd.MM.yyyy").format(dateOfBirth.getTime());
			}
		}
		return "";
	}
	

	/**
	 * not dynamically - could be done like displaying all personal attributes
	 * available in user profile - may be done in future work
	 * 
	 * @return
	 */
	@Transient
	public LocationAttribute getLocation() {
		for (Attribute attribute : attributes) {
			if (attribute.getClass().equals(LocationAttribute.class))
				return ((LocationAttribute) attribute);
		}
		return null;
	}

	/**
	 * Timeline is vizualised with WorkAndEducationAttributes
	 * 
	 * @return sorted map where key is year and value is list of
	 *         WorkAndEducationAttributes started in this year
	 */
	@Transient
	public Map<Integer, List<WorkAndEducationAttribute>> getTimeline() {
		Map<Integer, List<WorkAndEducationAttribute>> timeline = new HashMap<>();
		List<WorkAndEducationAttribute> timelineAttributes = getWorkAndEducationAttributes();

		for (WorkAndEducationAttribute we : timelineAttributes) {
			int key = we.getBegin().get(Calendar.YEAR);
			if (timeline.containsKey(key)) {
				List<WorkAndEducationAttribute> list = timeline.get(key);
				list.add(we);
				timeline.put(key, list);
			} else {
				List<WorkAndEducationAttribute> mapValue = new ArrayList<>();
				mapValue.add(we);
				timeline.put(key, mapValue);
			}
		}
		Map<Integer, List<WorkAndEducationAttribute>> sortedTimeline = new TreeMap<>(
				timeline);
		return sortedTimeline;
	}

	/**
	 * get all work and education attributes
	 * 
	 * @return list of work and education attributes
	 */
	@Transient
	public List<WorkAndEducationAttribute> getWorkAndEducationAttributes() {
		List<WorkAndEducationAttribute> workAndEducationAttributes = new ArrayList<>();
		for (Attribute attribute : attributes) {
			if (attribute.getClass().equals(WorkAndEducationAttribute.class))
				workAndEducationAttributes.add((WorkAndEducationAttribute) attribute);
		}
		
		return workAndEducationAttributes;
	}
	/**
	 * get all acquired user competencies
	 * 
	 * @return list of competencies
	 */
	@Transient
	public List<CompetenceAndInterestAttribute> getCompetencies() {
		List<CompetenceAndInterestAttribute> competencies = new ArrayList<>();
		for (Attribute attribute : attributes) {
			if (attribute.getClass().equals(CompetenceAttribute.class))
				competencies.add((CompetenceAttribute) attribute);
		}
		return competencies;
	}

	/**
	 * get all user interests
	 * 
	 * @return list of interests
	 */
	@Transient
	public List<CompetenceAndInterestAttribute> getInterests() {
		List<CompetenceAndInterestAttribute> interests = new ArrayList<>();
		for (Attribute attribute : attributes) {
			if (attribute.getClass().equals(InterestAttribute.class))
				interests.add((InterestAttribute) attribute);
		}
		return interests;
	}

	/**
	 * get all interests user is not interested in
	 * 
	 * @return list of non-interests
	 */
	@Transient
	public List<CompetenceAndInterestAttribute> getNonInterests() {
		List<CompetenceAndInterestAttribute> noninterests = new ArrayList<>();
		for (Attribute attribute : attributes) {
			if (attribute.getClass().equals(NonInterestAttribute.class))
				noninterests.add((NonInterestAttribute) attribute);
		}
		return noninterests;
	}

	/**
	 * get all  user availabilities
	 * 
	 * @return list of competencies
	 */
	@Transient
	public List<AvailabilityAttribute> getAvailabilities() {
		List<AvailabilityAttribute> availabilities = new ArrayList<>();
		for (Attribute attribute : attributes) {
			if (attribute.getClass().equals(AvailabilityAttribute.class))
				availabilities.add((AvailabilityAttribute) attribute);
		}
		return availabilities;
	}
	
	@Transient
	/**
	 * This method checks if a user has gained a certain competence. It differs 3 cases:
	 * 1. if the user has the competence itself
	 * 2. if the competence is a level of a parent-competence, and the user may has gained a higher level of the parent competence
	 * 3. if the competence has several levels, and the user has gained one of them
	 * 4. if the competence is a sub level of an already reached competence
	 * @param locDefinition
	 * @return
	 */
	public boolean hasCompetence(LOCdefinition locDefinition, Calendar date) {

		// 1. case: check for competence
		for (CompetenceAndInterestAttribute competence : this.getCompetencies()) {
			if (competence.getLOCdefinition().equals(locDefinition.getId())) {
				if (competence.getExpireDate() != null
						&& Calendar.getInstance().compareTo(
								competence.getExpireDate()) > 0)
					return false;

				if (competence.getDate() != null
						&& competence.getDate().compareTo(date) >= 0)
					return false;

				return true;
			}
		}
		// 2. case: check for higher level
		for (LOCrel rel : locDefinition.getParents()) {
			if (rel.getScheme() == LOCscheme.isDefinedLevelOf) {
				BigDecimal level = rel.getNumber();
				LOCtypeLOC parent = rel.getLocType();

				for (LOCrel potential_silbling : parent
						.getChildren(LOCscheme.hasDefinedLevel)) {
					if (potential_silbling.getNumber().compareTo(level) > 0)
						for (CompetenceAndInterestAttribute competence : this
								.getCompetencies()) {
							if (competence.getLOCdefinition().equals(
									potential_silbling.getLocType().getId())) {
								if (competence.getExpireDate() != null
										&& Calendar.getInstance().compareTo(
												competence.getExpireDate()) > 0)
									return false;

								if (competence.getDate() != null
										&& competence.getDate().compareTo(date) >= 0)
									return false;

								return true;
							}
						}
				}
			}
		}

		// 3. case: check for sub-levels
		for (LOCrel rel : locDefinition.getChildren(LOCscheme.hasDefinedLevel)) {
			for (CompetenceAndInterestAttribute competence : this
					.getCompetencies()) {
				if (competence.getLOCdefinition().equals(
						rel.getLocType().getId())) {
					if (competence.getExpireDate() != null
							&& Calendar.getInstance().compareTo(
									competence.getExpireDate()) > 0)
						return false;

					if (competence.getDate() != null
							&& competence.getDate().compareTo(date) >= 0)
						return false;

					return true;
				}
			}
		}

		// 4. case: check for parent competence if the current one is a
		// sub-level
		for (LOCrel rel : locDefinition.getParents()) {
			if (rel.getScheme() == LOCscheme.isDefinedLevelOf) {
				LOCtypeLOC parent = rel.getLocType();
				if (parent instanceof LOCdefinition) {
					return this.hasParentCompetence((LOCdefinition) parent,
							date);
				}
			}
		}
		return false;
	}

	@Transient
	/**
	 * This method checks if a user has gained a certain competence. It differs 3 cases:
	 * 1. if the user has the competence itself
	 * 2. if the competence is a level of a parent-competence, and the user may has gained a higher level of the parent competence
	 * 4. if the competence is a sub level of an already reached competence
	 * @param locDefinition
	 * @return
	 */
	public boolean hasParentCompetence(LOCdefinition locDefinition,
			Calendar date) {

		// 1. case: check for competence
		for (CompetenceAndInterestAttribute competence : this.getCompetencies()) {
			if (competence.getLOCdefinition().equals(locDefinition.getId())) {
				if (competence.getExpireDate() != null
						&& Calendar.getInstance().compareTo(
								competence.getExpireDate()) > 0)
					return false;

				if (competence.getDate() != null
						&& competence.getDate().compareTo(date) >= 0)
					return false;

				return true;
			}
		}
		// 2. case: check for higher level
		for (LOCrel rel : locDefinition.getParents()) {
			if (rel.getScheme() == LOCscheme.isDefinedLevelOf) {
				BigDecimal level = rel.getNumber();
				LOCtypeLOC parent = rel.getLocType();

				for (LOCrel potential_silbling : parent
						.getChildren(LOCscheme.hasDefinedLevel)) {
					if (potential_silbling.getNumber().compareTo(level) > 0)
						for (CompetenceAndInterestAttribute competence : this
								.getCompetencies()) {
							if (competence.getLOCdefinition().equals(
									potential_silbling.getLocType().getId())) {
								if (competence.getExpireDate() != null
										&& Calendar.getInstance().compareTo(
												competence.getExpireDate()) > 0)
									return false;

								if (competence.getDate() != null
										&& competence.getDate().compareTo(date) >= 0)
									return false;

								return true;
							}
						}
				}
			}
		}

		// 4. case: check for parent competence if the current one is a
		// sub-level
		for (LOCrel rel : locDefinition.getParents()) {
			if (rel.getScheme() == LOCscheme.isDefinedLevelOf) {
				LOCtypeLOC parent = rel.getLocType();
				if (parent instanceof LOCdefinition) {
					return this.hasParentCompetence((LOCdefinition) parent,
							date);
				}
			}
		}
		return false;
	}

	/**
	 * check if user has interest
	 * 
	 * @param locDefinition
	 * @return
	 */
	@Transient
	public boolean hasInterest(LOCdefinition locDefinition) {
		for (CompetenceAndInterestAttribute interest : this.getInterests()) {
			if (interest.getLOCdefinition().equals(locDefinition.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * check if user is not interest
	 * 
	 * @param locDefinition
	 * @return true when not interested
	 */
	@Transient
	public boolean hasNonInterest(LOCdefinition locDefinition) {
		for (CompetenceAndInterestAttribute noninterest : this
				.getNonInterests()) {
			if (noninterest.getLOCdefinition().equals(locDefinition.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * attribute is displayed in bubble graph if user has competence or any
	 * sub-competence
	 * 
	 * @param locDefinition
	 * @param type
	 *            - competence/interest/noninterest
	 * @param date
	 *            - check if competence is reached before a special date
	 * @return
	 */
	@Transient
	public boolean hasAttributeForBubbleGraph(LOCdefinition locDefinition,
			String type, Calendar date) {
		boolean hasAttr = false;
		switch (type) {
		case "competence":
		case "competencepastone":
		case "competencepasttwo":
		case "competence_zoom":
			hasAttr = this.hasCompetence(locDefinition, date);
			break;
		case "interest":
		case "interest_zoom":
			hasAttr = this.hasInterest(locDefinition);
			break;
		case "noninterest":
		case "noninterest_zoom":
			hasAttr = this.hasNonInterest(locDefinition);
			break;
		}

		if (hasAttr)
			return true;

		List<LOCrel> childs = locDefinition.getChildren(LOCscheme.hasPart);
		for (LOCrel rel : childs) {
			hasAttr = hasAttributeForBubbleGraph(
					(LOCdefinition) rel.getLocType(), type, date);
			if (hasAttr)
				return true;
		}

		childs = locDefinition.getChildren(LOCscheme.hasDefinedLevel);
		for (LOCrel rel : childs) {
			hasAttr = hasAttributeForBubbleGraph(
					(LOCdefinition) rel.getLocType(), type, date);
			if (hasAttr)
				return true;
		}

		childs = locDefinition.getChildren(LOCscheme.hasExample);
		for (LOCrel rel : childs) {
			hasAttr = hasAttributeForBubbleGraph(
					(LOCdefinition) rel.getLocType(), type, date);
			if (hasAttr)
				return true;
		}

		return false;
	}

	/**
	 * Used to get attributes by name, if they exist
	 * 
	 * @return
	 */
	@Transient
	public String getAttribute(String name) {
		for (Attribute attribute : attributes) {
			if (attribute.getName().equals(name))
				return ((StringAttribute) attribute).getValue();
		}
		return "";
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
}
