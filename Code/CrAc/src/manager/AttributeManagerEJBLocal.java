package manager;

import javax.ejb.Local;

import model.Attribute;

/**
 * Entity manager for attributes
 * 
 * @author Florian Jungwirth, Michaela Murauer
 * 
 */
@Local
public interface AttributeManagerEJBLocal {
	public boolean insert(Attribute attribute);

	public void delete(Attribute attribute);

	public Attribute getAttributeById(int ID);

	public void update();
}
