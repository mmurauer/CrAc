package manager;

import javax.ejb.Local;

import model.Issuer;

/**
 * Entity manager for issuers
 * 
 * @author Florian Jungwirth, Michaela Murauer
 * 
 */
@Local
public interface IssuerManagerEJBLocal {
	public boolean insert(Issuer issuer);

	public void delete(Issuer issuer);

	public Issuer getIssuerById(int ID);

	public void update();
}
