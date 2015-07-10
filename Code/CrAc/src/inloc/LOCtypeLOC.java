package inloc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * The sketch of this class was generated by JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2,
 * based on the InLOC xsd.
 * 
 * This class represents the similarities shared between LOCstrucutre and LOCdefinition, it therefore is the superclass of them.
 * 
 * for further reading please visit http://www.cetis.org.uk/inloc/coarse/IM.html
 * 
 * @author Florian Jungwirth, Michaela Murauer
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LOCtypeLOC", propOrder = {
    "extraID",
    "title",
    "abbr",
    "description",
    "furtherInformation",
    "rights",
    "created",
    "modified",
    "issued",
    "validityStart",
    "validityEnd",
    "version"
})
@XmlSeeAlso({
    LOCstructure.class,
    LOCdefinition.class
})
public abstract class LOCtypeLOC {

    @XmlSchemaType(name = "anyURI")
    protected List<String> extraID;
    protected List<LOCtypeMultilingualToken> title;
    protected List<LOCtypeMultilingualToken> abbr;
    protected List<LOCtypeMultilingualString> description;
    protected List<LOCtypeMultilingualStringAnyAttribute> furtherInformation;
    protected List<LOCtypeMultilingualString> rights;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar created;
    @XmlSchemaType(name = "dateTime")
    protected List<XMLGregorianCalendar> modified;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar issued;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar validityStart;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar validityEnd;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String version;
    @XmlAttribute(name = "id", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String id;
    @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace")
    protected String lang;


    @XmlTransient
    protected List<LOCrel> childs;
    @XmlTransient
	protected List<LOCrel> parents;
    
    public LOCtypeLOC() {
    	childs = new ArrayList<LOCrel>();
    	parents = new ArrayList<LOCrel>();
    }
    
    public void addChild(LOCscheme scheme, LOCtypeLOC definition) {
    	childs.add(new LOCrel(scheme, definition));
    }
    
    public void addParent(LOCscheme scheme, LOCtypeLOC definition) {
    	parents.add(new LOCrel(scheme, definition));
    }
    
    public void addChild(LOCscheme scheme, LOCtypeLOC definition, BigDecimal number) {
    	childs.add(new LOCrel(scheme, definition, number));
    }
    
    public void addParent(LOCscheme scheme, LOCtypeLOC definition, BigDecimal number) {
    	parents.add(new LOCrel(scheme, definition, number));
    }
    
    public List<LOCrel> getChildren(LOCscheme scheme) {
    	ArrayList<LOCrel> parts = new ArrayList<LOCrel>();
    	for(LOCrel rel : childs) {
    		if(rel.getScheme().equals(scheme))
    			parts.add(rel);
    	}
    	
    	return parts;
    }
    
    public List<LOCrel> getParents(LOCscheme scheme) {
    	ArrayList<LOCrel> parts = new ArrayList<LOCrel>();
    	for(LOCrel rel : parents) {
    		if(rel.getScheme().equals(scheme))
    			parts.add(rel);
    	}
    	
    	return parts;
    }
    
    public List<LOCrel> getParents() {
    	return this.parents;
    }
    
    /**
     * Gets the value of the extraID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extraID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtraID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getExtraID() {
        if (extraID == null) {
            extraID = new ArrayList<String>();
        }
        return this.extraID;
    }

    /**
     * Gets the value of the title property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the title property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTitle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LOCtypeMultilingualToken }
     * 
     * 
     */
    public List<LOCtypeMultilingualToken> getTitle() {
        if (title == null) {
            title = new ArrayList<LOCtypeMultilingualToken>();
        }
        return this.title;
    }

    public void addTitle(LOCtypeMultilingualToken title) {
    	if (this.title == null) {
            this.title = new ArrayList<LOCtypeMultilingualToken>();
        }
    	this.title.add(title);
    }
    
    /**
     * Gets the value of the abbr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the abbr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAbbr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LOCtypeMultilingualToken }
     * 
     * 
     */
    public List<LOCtypeMultilingualToken> getAbbr() {
        if (abbr == null) {
            abbr = new ArrayList<LOCtypeMultilingualToken>();
        }
        return this.abbr;
    }

    /**
     * Gets the value of the description property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the description property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LOCtypeMultilingualString }
     * 
     * 
     */
    public List<LOCtypeMultilingualString> getDescription() {
        if (description == null) {
            description = new ArrayList<LOCtypeMultilingualString>();
        }
        return this.description;
    }

    public void addDescription(LOCtypeMultilingualString description) {
    	if (this.description == null) {
            this.description = new ArrayList<LOCtypeMultilingualString>();
        }
    	this.description.add(description);
    }
    /**
     * Gets the value of the furtherInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the furtherInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFurtherInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LOCtypeMultilingualStringAnyAttribute }
     * 
     * 
     */
    public List<LOCtypeMultilingualStringAnyAttribute> getFurtherInformation() {
        if (furtherInformation == null) {
            furtherInformation = new ArrayList<LOCtypeMultilingualStringAnyAttribute>();
        }
        return this.furtherInformation;
    }

    /**
     * Gets the value of the rights property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rights property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRights().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LOCtypeMultilingualString }
     * 
     * 
     */
    public List<LOCtypeMultilingualString> getRights() {
        if (rights == null) {
            rights = new ArrayList<LOCtypeMultilingualString>();
        }
        return this.rights;
    }

    /**
     * Gets the value of the created property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreated() {
        return created;
    }

    /**
     * Sets the value of the created property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreated(XMLGregorianCalendar value) {
        this.created = value;
    }

    /**
     * Gets the value of the modified property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the modified property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModified().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XMLGregorianCalendar }
     * 
     * 
     */
    public List<XMLGregorianCalendar> getModified() {
        if (modified == null) {
            modified = new ArrayList<XMLGregorianCalendar>();
        }
        return this.modified;
    }

    /**
     * Gets the value of the issued property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getIssued() {
        return issued;
    }

    /**
     * Sets the value of the issued property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setIssued(XMLGregorianCalendar value) {
        this.issued = value;
    }

    /**
     * Gets the value of the validityStart property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidityStart() {
        return validityStart;
    }

    /**
     * Sets the value of the validityStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidityStart(XMLGregorianCalendar value) {
        this.validityStart = value;
    }

    /**
     * Gets the value of the validityEnd property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidityEnd() {
        return validityEnd;
    }

    /**
     * Sets the value of the validityEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidityEnd(XMLGregorianCalendar value) {
        this.validityEnd = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

	@Override
	public String toString() {
		String titleToString = "";
		if(title != null){
			for(LOCtypeMultilingualToken title_single : title){
				if(title_single.getLang().equals("en"))
					titleToString = title_single.getValue();	
			}
		}
		
		String descriptionToString = "";
		if(description != null){
			for(LOCtypeMultilingualString description_single : description){
				if(description_single.getLang().equals("en"))
					descriptionToString = description_single.getValue();
			}
		}
		return (title != null) ? titleToString : descriptionToString;
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LOCtypeLOC other = (LOCtypeLOC) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}