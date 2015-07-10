package manager;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Issuer;

/**
 * Session Bean implementation class IssuerManagerEJB
 * 
 * @author Florian Jungwirth, Michaela Murauer
 */
@Stateful
@LocalBean
public class IssuerManagerEJB implements IssuerManagerEJBLocal {

	@PersistenceContext(unitName = "bakk_server", type = javax.persistence.PersistenceContextType.EXTENDED)
	EntityManager em;

	public IssuerManagerEJB() {
	}

	/**
	 * insert issuer in database
	 * 
	 * @param issuer
	 * @return
	 */
	public boolean insert(Issuer issuer) {
		em.persist(issuer);
		return true;
	}

	/**
	 * delete issuer from database
	 * 
	 * @param issuer
	 */
	public void delete(Issuer issuer) {
		em.remove(issuer);
	}

	/**
	 * get issure with ID = ID from persistence context
	 * 
	 * @param ID
	 * @return
	 */
	public Issuer getIssuerById(int ID) {
		return em.find(Issuer.class, ID);
	}

	/**
	 * update persistence context, as JavaEE application no code is needed here
	 * transaction commited by injecting the EJB - important: has to be
	 * annotated with Stateful NOTE: use this for persisting all types of issuer
	 * (user/task)
	 */
	public void update() {
	}
}
