package inloc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * The sketch of this class was generated by JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2,
 * based on the InLOC xsd.
 * 
 * @author Florian Jungwirth, Michaela Murauer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SchemeObject", propOrder = {
    "label"
})
public class SchemeObject {

    protected List<LOCtypeMultilingualToken> label;
    @XmlAttribute(name = "id")
    @XmlSchemaType(name = "anyURI")
    protected String id;

    /**
     * Gets the value of the label property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the label property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLabel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LOCtypeMultilingualToken }
     * 
     * 
     */
    public List<LOCtypeMultilingualToken> getLabel() {
        if (label == null) {
            label = new ArrayList<LOCtypeMultilingualToken>();
        }
        return this.label;
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

}
