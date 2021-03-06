package inloc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * The sketch of this class was generated by JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2,
 * based on the InLOC xsd.
 * 
Most learning outcome and competence definitions exist as part of a structure or framework.

A structure arranges learning outcomes or competences using relationships between them.
They may be grouped together under a heading or can be arranged in a tree or other structure.
LOCs can be made up of other LOCs that may be required or optional. There may also be rules about how LOCs can be combined.
 * 
 * for further reading please visit http://www.cetis.org.uk/inloc/coarse/IM.html
 * 
 * @author Florian Jungwirth, Michaela Murauer
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LOCstructure", propOrder = { "combinationRules", "locTypes",
		"locAssociations" })
public class LOCstructure extends LOCtypeLOC {

	protected List<LOCtypeMultilingualString> combinationRules;

	@XmlElements({
			@XmlElement(name = "LOCstructure", type = LOCstructure.class),
			@XmlElement(name = "LOCdefinition", type = LOCdefinition.class) })
	protected List<LOCtypeLOC> locTypes;

	@XmlElement(name = "LOCassociation", type = LOCassociation.class)
	protected List<LOCassociation> locAssociations;

	@XmlTransient
	private Map<BigDecimal, LOCdefinition> levels;
	
	public LOCstructure() {
		super();

		this.locTypes = new ArrayList<LOCtypeLOC>();
		this.locAssociations = new ArrayList<LOCassociation>();
		this.levels = new HashMap<BigDecimal,LOCdefinition>();
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public LOCtypeLOC getLOCtypeById(String id) {
		if(this.id.equals(id))
			return this;
		
		for (Object loc : locTypes) {
			if (((LOCtypeLOC) loc).getId().equals(id))
				return (LOCtypeLOC) loc;
		}

		return null;
	}

	public LOCdefinition getLOCdefinitionById(String id) {
		LOCtypeLOC loc = getLOCtypeById(id);

		if (loc != null && loc instanceof LOCdefinition)
			return (LOCdefinition) loc;

		return null;
	}

	/**
	 * Gets the value of the combinationRules property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the combinationRules property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getCombinationRules().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link LOCtypeMultilingualString }
	 * 
	 * 
	 */
	public List<LOCtypeMultilingualString> getCombinationRules() {
		if (combinationRules == null) {
			combinationRules = new ArrayList<LOCtypeMultilingualString>();
		}
		return this.combinationRules;
	}

	/**
	 * Gets the value of the loCstructureOrLOCdefinitionOrLOCassociation
	 * property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the
	 * loCstructureOrLOCdefinitionOrLOCassociation property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getLOCstructureOrLOCdefinitionOrLOCassociation().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link LOCstructure } {@link LOCdefinition }
	 * 
	 * 
	 */
	public List<LOCtypeLOC> getLOCtypes() {
		if (locTypes == null) {
			locTypes = new ArrayList<LOCtypeLOC>();
		}
		return this.locTypes;
	}

	public List<LOCassociation> getLocAssociations() {
		if (this.locAssociations == null)
			locAssociations = new ArrayList<LOCassociation>();

		return this.locAssociations;
	}

	public void buildLOCtree() {
		for (LOCassociation a : locAssociations) {
			// LOCrel
			if (a.getType().equals("http://purl.org/net/inloc/LOCrel")) {

				LOCtypeLOC subject;
				LOCtypeLOC object;

				subject = getLOCtypeById(a.getSubject().getId());
				object = getLOCtypeById(a.getObject().getId());
				
				switch (a.getScheme().getId()) {
				// hasLOCpart
				case "http://purl.org/net/inloc/hasLOCpart":

					//System.out.println(subject + " hasPart " + object);

					if (subject != null && object != null) {
						subject.addChild(LOCscheme.hasPart, object);
						object.addParent(LOCscheme.isPartOf, subject);
					}
					break;
				// hasExample
				case "http://purl.org/net/inloc/hasExample":

					//System.out.println(subject + " hasExample " + object);

					if (subject != null && object != null && object instanceof LOCdefinition) {
						subject.addChild(LOCscheme.hasExample, object);
						object.addParent(LOCscheme.isExampleOf, subject);
					}
					break;
				/* hasDefinedLevel
				 * 
				 * When a (subject) LOCdefinition hasDefinedLevel another (object) LOCdefinition, it means that the object stands as a level specific to that particular subject.
				 * When a LOCstructure hasDefinedLevel a LOCdefinition, that definition is serving as a generic level throughout the LOCstructure.
				 * A LOCdefinition cannot have a LOCstructure as a defined level.
				 */
				case "http://purl.org/net/inloc/hasDefinedLevel":

					if (subject != null && object != null) {
						if(subject instanceof LOCstructure && object instanceof LOCdefinition) {
							subject.addChild(LOCscheme.hasDefinedLevel, object);
							object.addParent(LOCscheme.isDefinedLevelOf, subject);
						}
						if(subject instanceof LOCdefinition && object instanceof LOCdefinition) {
							
							BigDecimal number = a.getNumber();
							subject.addChild(LOCscheme.hasDefinedLevel, object, number);
							object.addParent(LOCscheme.isDefinedLevelOf, subject, number);
							
							//System.out.println(subject + " hasDefinedLevel (" + number + ") " + object);
						}
					}

					break;
				}
			}
			else if(a.getType().equals("http://purl.org/net/inloc/level")) {
				LOCdefinition subject = (LOCdefinition)getLOCtypeById(a.getSubject().getId());
				BigDecimal number = a.getNumber();
				this.levels.put(number, subject);
			}

		}
	}

	public Map<BigDecimal, LOCdefinition> getLevels() {
		return levels;
	}

	public LOCdefinition getLevel(Integer i) {
		return this.levels.get(i);
	}
	
	public void addLevel(BigDecimal number, LOCdefinition loc) {
		if(!this.levels.containsKey(number))
			this.levels.put(number, loc);
	}

}
