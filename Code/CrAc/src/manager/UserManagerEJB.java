package manager;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.User;

/**
 * Session Bean implementation class UserManagerEJB
 * 
 * @author Florian Jungwirth, Michaela Muruaer
 * 
 */
@Stateful
@LocalBean
public class UserManagerEJB implements UserManagerEJBLocal {

	@PersistenceContext(unitName="bakk_server", type=javax.persistence.PersistenceContextType.EXTENDED)
	EntityManager em;
	
    public UserManagerEJB() {
    }
    
    /**
	 * get user with ID = ID from persistence context
	 * 
	 * @param ID
	 * @return
	 */
	public User getUserById(int ID){
		return em.find(User.class, ID);
	}
	
	/**
	 * get all user from database
	 * 
	 * @return list of all user
	 */
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers(){
		return em.createNamedQuery("User.getAll").getResultList();
	}
	
	/**
	 * update persistence context, as JavaEE application no code is needed here
	 * transaction commited by injecting the EJB - important: has to be
	 * annotated with Stateful NOTE: can be used if only user have been
	 * modified (no task) - better use IssuerManagerEJB.update
	 */
	public void update(){
	}
}
