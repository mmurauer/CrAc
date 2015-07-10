package manager;

import java.util.List;

import javax.ejb.Local;

import model.Task;

/**
 * Entity manager for attributes - use insert and delete from IsserManagerEJB as
 * this is parent class
 * 
 * @author Florian Jungwirth, Michaela Murauer
 * 
 */
@Local
public interface TaskManagerEJBLocal {
	public List<Task> getAllTasks();

	public Task getTaskById(int ID);

	public void update();
}
