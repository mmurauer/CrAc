package manager;

import java.util.List;

import javax.ejb.Local;

import model.User;

/**
 * Entity manager for attributes - use insert and delete from IsserManagerEJB as
 * this is parent class
 * 
 * @author Florian Jungwirth, Michaela Murauer
 * 
 */
@Local
public interface UserManagerEJBLocal {
	public User getUserById(int ID);
	public List<User> getAllUsers();
	public void update();
}
