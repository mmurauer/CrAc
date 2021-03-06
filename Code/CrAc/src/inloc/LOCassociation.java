package inloc;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * The sketch of this class was generated by JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2,
 * based on the InLOC xsd.
 * 
 * LOC association is a hybrid class – one structure serving two different roles.
 * Firstly, it represents the structure of a LOCstructure, relating LOCdefinitions together.
 * Secondly, it provides a structure to hold properties that have more than one piece of information in them – called "compound properties".
 * One set of these compound properties is the well-known Dublin Core ones relating a resource to agents, as creator, contributor, publisher and rights holder.
 * Features of the InLOC Model, above, provides more detail.
 * 
 * for further reading please visit http://www.cetis.org.uk/inloc/coarse/IM.html#LOCassociation
 * 
 * @author Florian Jungwirth, Michaela Murauer
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LOCassociation", propOrder = {
    "subject",
    "scheme",
    "object",
    "number"
})
public class LOCassociation {

    @XmlElement(required = true)
    protected Subject subject;
    @XmlElement(required = true)
    protected SchemeObject scheme;
    @XmlElement(required = true)
    protected SchemeObject object;
    protected BigDecimal number;
    @XmlAttribute(name = "id")
    @XmlSchemaType(name = "anyURI")
    protected String id;
    @XmlAttribute(name = "type", required = true)
    protected String type;

    /**
     * Gets the value of the subject property.
     * 
     * @return
     *     possible object is
     *     {@link Subject }
     *     
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *     allowed object is
     *     {@link Subject }
     *     
     */
    public void setSubject(Subject value) {
        this.subject = value;
    }

    /**
     * Gets the value of the scheme property.
     * 
     * @return
     *     possible object is
     *     {@link SchemeObject }
     *     
     */
    public SchemeObject getScheme() {
        return scheme;
    }

    /**
     * Sets the value of the scheme property.
     * 
     * @param value
     *     allowed object is
     *     {@link SchemeObject }
     *     
     */
    public void setScheme(SchemeObject value) {
        this.scheme = value;
    }

    /**
     * Gets the value of the object property.
     * 
     * @return
     *     possible object is
     *     {@link SchemeObject }
     *     
     */
    public SchemeObject getObject() {
        return object;
    }

    /**
     * Sets the value of the object property.
     * 
     * @param value
     *     allowed object is
     *     {@link SchemeObject }
     *     
     */
    public void setObject(SchemeObject value) {
        this.object = value;
    }

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNumber(BigDecimal value) {
        this.number = value;
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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

	@Override
	public String toString() {
		return "LOCassociation [subject=" + subject + ", scheme=" + scheme
				+ ", object=" + object + ", type=" + type + "]";
	}

    
}
