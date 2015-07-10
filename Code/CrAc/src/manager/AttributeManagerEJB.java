package manager;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Attribute;

/**
 * Session Bean implementation class AttributeManagerEJB
 * 
 * @author Florian Jungwirth, Michaela Muruaer
 * 
 */
@Stateful
@LocalBean
public class AttributeManagerEJB implements AttributeManagerEJBLocal {

	@PersistenceContext(unitName = "bakk_server", type = javax.persistence.PersistenceContextType.EXTENDED)
	EntityManager em;

	public AttributeManagerEJB() {
	}

	/**
	 * insert attribute in database - this function is never used and only
	 * implemented to be complete, would only be used if attribute can exist
	 * without being attached to a issuer
	 * 
	 * @param attribute
	 * @return
	 */
	public boolean insert(Attribute attribute) {
		em.persist(attribute);
		return true;
	}

	/**
	 * delete attribute from database
	 * 
	 * @param attribute
	 */
	public void delete(Attribute attribute) {
		em.remove(attribute);
	}

	/**
	 * get attribute with ID = ID from persistence context
	 * 
	 * @param ID
	 * @return
	 */
	public Attribute getAttributeById(int ID) {
		return em.find(Attribute.class, ID);
	}

	/**
	 * update persistence context, as JavaEE application no code is needed here
	 * transaction commited by injecting the EJB - important: has to be
	 * annotated with Stateful NOTE: only needed if there are attributes without
	 * any connection to issuer, use
	 * UserManagerEJB.update/TaskManagerEJB.update/IssuerManagerEJB.update
	 * instead
	 */
	public void update() {
	}
}
