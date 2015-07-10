package manager;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Evidence;

/**
 * Session Bean implementation class EvidenceManagerEJB
 * 
 * @author Florian Jungwirth, Michaela Muruaer
 * 
 */
@Stateful
@LocalBean
public class EvidenceManagerEJB implements EvidenceManagerEJBLocal {

	@PersistenceContext(unitName = "bakk_server", type = javax.persistence.PersistenceContextType.EXTENDED)
	EntityManager em;

	public EvidenceManagerEJB() {
	}

	/**
	 * insert evidence in database - this function is never used and only
	 * implemented to be complete, use if insert evidence without connection to
	 * a issuer/attribute
	 * 
	 * @param evidence
	 * @return
	 */
	public boolean insert(Evidence evidence) {
		em.persist(evidence);
		return true;
	}

	/**
	 * delete evidence from database
	 * 
	 * @param evidence
	 */
	public void delete(Evidence evidence) {
		em.remove(evidence);
	}

	/**
	 * get evidence with ID = ID from persistence context
	 * 
	 * @param ID
	 * @return
	 */
	public Evidence getEvidenceById(int ID) {
		return em.find(Evidence.class, ID);
	}
	
	/**
	 * update persistence context, as JavaEE application no code is needed here
	 * transaction commited by injecting the EJB - important: has to be
	 * annotated with Stateful NOTE: only needed if there are attributes without
	 * any connection to issuer or attribute, use
	 * UserManagerEJB.update/TaskManagerEJB.update/IssuerManagerEJB.update
	 * instead
	 */
	public void update() {
	}
}
