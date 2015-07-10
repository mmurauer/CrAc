package manager;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Task;

/**
 * Session Bean implementation class TaskManagerEJB
 * 
 * @author Florian Jungwirth, Michaela Muruaer
 * 
 */
@Stateful
@LocalBean
public class TaskManagerEJB implements TaskManagerEJBLocal {

	@PersistenceContext(unitName = "bakk_server", type = javax.persistence.PersistenceContextType.EXTENDED)
	EntityManager em;

	public TaskManagerEJB() {
	}

	/**
	 * get task with ID = ID from persistence context
	 * 
	 * @param ID
	 * @return
	 */
	public Task getTaskById(int ID) {
		return em.find(Task.class, ID);
	}

	/**
	 * get all tasks from database
	 * 
	 * @return list of all tasks
	 */
	@SuppressWarnings("unchecked")
	public List<Task> getAllTasks() {
		return em.createNamedQuery("Task.getAll").getResultList();
	}

	/**
	 * update persistence context, as JavaEE application no code is needed here
	 * transaction commited by injecting the EJB - important: has to be
	 * annotated with Stateful NOTE: can be used if only tasks have been
	 * modified (no user) - better use IssuerManagerEJB.update
	 */
	public void update() {
	}
}
