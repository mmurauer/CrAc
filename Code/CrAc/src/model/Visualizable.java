package model;

import java.util.List;

/**
 * interface used for displaying user and tasks in compare.jsp
 * @author Florian Jungwirth, Michaela Murauer
 *
 */
public interface Visualizable {
	public List<CompetenceAndInterestAttribute> getCompetencies();
	public LocationAttribute getLocation();
	public String getName();
}
