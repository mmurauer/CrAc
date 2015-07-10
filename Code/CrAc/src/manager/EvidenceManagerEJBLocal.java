package manager;

import javax.ejb.Local;

import model.Evidence;

/**
 * Entity manager for evidence
 * 
 * @author Florian Jungwirth, Michaela Murauer
 * 
 */
@Local
public interface EvidenceManagerEJBLocal {
	public boolean insert(Evidence evidence);

	public void delete(Evidence evidence);

	public Evidence getEvidenceById(int ID);

	public void update();
}
