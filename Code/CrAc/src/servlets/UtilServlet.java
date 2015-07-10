package servlets;

import java.io.IOException;
import java.util.GregorianCalendar;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.IssuerManagerEJBLocal;
import model.Address;
import model.CompetenceAttribute;
import model.DateAttribute;
import model.Evidence;
import model.InterestAttribute;
import model.LocationAttribute;
import model.NonInterestAttribute;
import model.Organization;
import model.RangeAttribute;
import model.StringAttribute;
import model.Task;
import model.User;
import model.WorkAndEducationAttribute;

/**
 * Servlet implementation class UtilServlet. It is bound to the deployment of the project and inserts demo users and tasks
 */
@WebServlet("/UtilServlet")
@RequestScoped
public class UtilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB 
	private IssuerManagerEJBLocal issuerManagerEJBLocal;

	public UtilServlet() {
		super();
	}

	public void init() throws ServletException {
		
		this.insertDemoTask();
		this.insertDemoUser();
		
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	private void insertDemoTask() {
		Task demo = new Task();
		
		// simple attributes
		demo.addAttribute(new StringAttribute("name", "Child Care Assistant"));
		demo.addAttribute(new StringAttribute(
				"description",
				"Helps in households and takes care of babies and children"));
		demo.addAttribute(new LocationAttribute("location", 0, 0, new Address(
				null, null, null, "Linz")));
		demo.addAttribute(new RangeAttribute("age", 18, 35));

		// competence attributes
		// driving licence
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/DrivingLicences/B"));

		// languages
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/Languages/E/1"));
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/Languages/G/2"));
		
		// social and personal
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/SocialResponsibility/Respect/1"));
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/SocialResponsibility/Helpfulness/1"));
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/SelfResponsibility/SelfReliance/1"));
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/Leadership/Decisions/1"));
	
		// social work
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialWork/ChildCare/Dealing/1"));
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialWork/ChildCare/EmotionalCare/2"));
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialWork/ChildCare/EmotionalCare/3"));
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialWork/ChildCare/PhysicalDevelopment/2"));
		
		// interest attributes
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Film/5"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Film/9"));

		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/19"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/21"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/22"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/34"));

		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/8"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/11"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/14"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/26"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/29"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/30"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/31"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/43"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/44"));

		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/PC-Games/2"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/PC-Games/8"));

		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/17"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/25"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/28"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/31"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/37"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/40"));

		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/8"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/12"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/17"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/34"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/35"));

		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/6"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/7"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/12"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/26"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/28"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/31"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/35"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/37"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/39"));
		 
		issuerManagerEJBLocal.insert(demo);
		
		Task demo2 = new Task();
		
		// simple attributes
		demo2.addAttribute(new StringAttribute("name", "Disabled / Handicaped Care Assistant"));
		demo2.addAttribute(new StringAttribute(
				"description",
				"Helps disabled people"));
		demo2.addAttribute(new LocationAttribute("location", 0, 0, new Address(
				"Bahnhofstraße", "1", "4020", "Linz")));
		demo2.addAttribute(new RangeAttribute("age", 18, 50));

		// competence attributes
		// driving licence
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/DrivingLicences/B"));

		// languages
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/Languages/E/1"));
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/Languages/G/2"));
		
		// social and personal
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/SocialResponsibility/Respect/1"));
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/SocialResponsibility/Helpfulness/1"));
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/SelfResponsibility/SelfReliance/1"));
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/Leadership/Decisions/2"));
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/Cooperation/Teamwork/2"));
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/Leadership/Responsibleness/2"));
		
		// social work
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialWork/DisabledCare/Medication/1"));
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialWork/DisabledCare/Support/2"));
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialWork/DisabledCare/Activities/2"));
		
		// interest attributes
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Film/5"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Film/9"));

		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/19"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/21"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/22"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/34"));

		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/11"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/14"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/29"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/30"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/31"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/44"));

		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/25"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/28"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/31"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/37"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/40"));

		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/8"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/12"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/17"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/34"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/35"));

		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/6"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/7"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/12"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/26"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/28"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/31"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/35"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/37"));
		demo2.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/39"));
		 
		issuerManagerEJBLocal.insert(demo2);
	}

	private void insertDemoUser() {
		//First demo user with most information provided
		User demo = new User();

		// organizations
		Organization htl = new Organization("HTL Linz");
		Organization jku_linz = new Organization("JKU Linz");
		Organization work_place = new Organization("Company A");
		Organization work_place2 = new Organization("Company B");

		// user with attributes
		// evidencies
		Evidence degree = new Evidence("a level", new GregorianCalendar(2011,
				7, 18), null, "1.0", htl);
		Evidence bachelor = new Evidence("Bachelor of Science",
				new GregorianCalendar(2015, 7, 20), null, "1.78", jku_linz);

		// simple attributes
		demo.addAttribute(new StringAttribute("lastname", "Smith"));
		demo.addAttribute(new StringAttribute("firstname", "Tom"));
		demo.addAttribute(new DateAttribute("birthday", new GregorianCalendar(
				1981, 2, 28)));

		Address address = new Address("Freistädterstraße", "5a", "Linz/Urfahr",
				"4040");
		demo.addAttribute(new LocationAttribute("location", 12, 12, address));

		WorkAndEducationAttribute education = new WorkAndEducationAttribute(
				"education", new GregorianCalendar(2006, 8, 1),
				new GregorianCalendar(2011, 5, 18), "Electronics & IT", htl);
		education.addEvidence(degree);
		demo.addAttribute(education);

		WorkAndEducationAttribute studies = new WorkAndEducationAttribute(
				"education", new GregorianCalendar(2011, 9, 1),
				new GregorianCalendar(2015, 7, 20), "Computer Science",
				jku_linz);
		studies.addEvidence(bachelor);
		demo.addAttribute(studies);

		WorkAndEducationAttribute internship = new WorkAndEducationAttribute(
				"work", new GregorianCalendar(2009, 6, 1),
				new GregorianCalendar(2009, 8, 31),
				"Internship PHP software development", work_place);
		demo.addAttribute(internship);

		WorkAndEducationAttribute work = new WorkAndEducationAttribute("work",
				new GregorianCalendar(2010, 7, 1), null,
				"Part time job PHP development", work_place2);
		demo.addAttribute(work);
		
		// competence attributes
		// driving licence
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/DrivingLicences/B", new GregorianCalendar(2009, 5, 1)));

		// languages
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/Languages/E/2", new GregorianCalendar(2011, 9, 1)));
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/Languages/G/3", new GregorianCalendar(2014, 6, 1)));
		
		// social and personal
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/SocialResponsibility/Respect/1", new GregorianCalendar(2013, 9, 1)));
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/SocialResponsibility/Helpfulness/1", new GregorianCalendar(2011, 9, 1)));
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/Leadership/Decisions/1"));
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/Cooperation/Teamwork/1", new GregorianCalendar(2013, 9, 1)));
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/Leadership/Responsibleness/2"));
		
		// social work
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialWork/DisabledCare/Medication/1", new GregorianCalendar(2013, 9, 1)));
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialWork/DisabledCare/Support/3", new GregorianCalendar(2013, 9, 1)));
		demo.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialWork/DisabledCare/Activities/1"));
		
		// interests
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Film/4"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Film/6"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Film/23"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Film/25"));

		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/6"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/10"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/15"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/19"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/29"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/32"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/33"));

		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/8"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/11"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/14"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/25"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/26"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/29"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/33"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/37"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/41"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/44"));

		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/PC-Games/2"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/PC-Games/10"));

		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/17"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/23"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/28"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/33"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/39"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/40"));

		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/9"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/34"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/39"));

		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/5"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/7"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/12"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/26"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/28"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/31"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/34"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/37"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/39"));
		demo.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/40"));

		// noninterests
		demo.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/Film/1"));
		demo.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/Film/2"));
		demo.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/Film/3"));
		demo.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/Film/15"));

		demo.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/1"));
		demo.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/2"));
		demo.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/3"));

		demo.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/PC-Games/1"));
		demo.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/PC-Games/3"));
		demo.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/PC-Games/5"));
		demo.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/PC-Games/6"));
		demo.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/PC-Games/7"));

		issuerManagerEJBLocal.insert(demo);
		
		//second demo user without competencies
		User demo0 = new User();

		// organizations
		Organization hak = new Organization("Handelsakademie Linz");
		Organization work_place3 = new Organization("Company C");

		// user with attributes
		// evidencies
		degree = new Evidence("a level", new GregorianCalendar(2012, 7, 12),
				null, "1.75", hak);
		bachelor = new Evidence("Bachelor of Laws", new GregorianCalendar(2015,
				6, 15), null, "2.54", jku_linz);

		// simple attributes
		demo0.addAttribute(new StringAttribute("lastname", "Doe"));
		demo0.addAttribute(new StringAttribute("firstname", "Jane"));
		demo0.addAttribute(new DateAttribute("birthday", new GregorianCalendar(
				1992, 5, 23)));

		address = new Address("Hauptplatz", "85", "Linz", "4020");
		demo0.addAttribute(new LocationAttribute("location", 12, 12, address));

		education = new WorkAndEducationAttribute("education",
				new GregorianCalendar(2006, 8, 1), new GregorianCalendar(2011,
						5, 18), "Economy", hak);
		education.addEvidence(degree);
		demo0.addAttribute(education);

		studies = new WorkAndEducationAttribute("education",
				new GregorianCalendar(2011, 9, 1), new GregorianCalendar(2015,
						7, 20), "Law", jku_linz);
		studies.addEvidence(bachelor);
		demo0.addAttribute(studies);

		internship = new WorkAndEducationAttribute("work",
				new GregorianCalendar(2012, 6, 1), new GregorianCalendar(2012,
						8, 31), "Internship lawyer assistant", work_place);
		demo0.addAttribute(internship);

		work = new WorkAndEducationAttribute("work", new GregorianCalendar(
				2012, 10, 1), null, "Part time job in law firm", work_place3);
		demo0.addAttribute(work);

		// interests
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Film/3"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Film/8"));

		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/8"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/12"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/14"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/16"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/17"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/18"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/25"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/31"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Music/32"));

		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/5"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/9"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/12"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/23"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/26"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Sports/43"));

		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/PC-Games/4"));

		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/8"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/9"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/11"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/15"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/22"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/27"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/32"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/37"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Recreation/40"));

		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/11"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/32"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/EnvironmentalTopics/35"));

		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/6"));
		demo0.addAttribute(new InterestAttribute("interest",
				"http://inloc.crac.at/interests/v2/Science/8"));
		// noninterests
		demo0.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/Film/6"));
		demo0.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/Film/8"));
		demo0.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/Film/10"));
		demo0.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/Film/17"));

		demo0.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/PC-Games/2"));
		demo0.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/PC-Games/6"));
		demo0.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/PC-Games/8"));
		demo0.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/PC-Games/9"));
		demo0.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/PC-Games/10"));

		demo0.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/Science/28"));
		demo0.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/Science/31"));
		demo0.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/Science/34"));
		demo0.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/Science/37"));
		demo0.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/Science/39"));
		demo0.addAttribute(new NonInterestAttribute("noninterest",
				"http://inloc.crac.at/interests/v2/Science/40"));

		// competence attributes
		// driving licence
		demo0.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/DrivingLicences/B"));

		// languages
		demo0.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/Languages/E/3"));
		demo0.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/Languages/G/3"));
		demo0.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/Languages/F/1"));
		
		// social and personal
		demo0.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/SocialResponsibility/Respect/2"));
		demo0.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/SocialResponsibility/Helpfulness/1"));
		demo0.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/SelfResponsibility/SelfReliance/2"));
		demo0.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/Leadership/Decisions/2"));
	
		// social work
		demo0.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialWork/ChildCare/Dealing/2"));
		demo0.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialWork/ChildCare/EmotionalCare/1"));
		
		issuerManagerEJBLocal.insert(demo0);

		//more demo users with just simple attributes
		User demo1 = new User();

		// simple attributes
		demo1.addAttribute(new StringAttribute("lastname", "Meier"));
		demo1.addAttribute(new StringAttribute("firstname", "Monika"));
		demo1.addAttribute(new DateAttribute("birthday", new GregorianCalendar(
				1989, 7, 12)));

		address = new Address("Bahnhofstraße", "1", "Linz", "4020");
		demo1.addAttribute(new LocationAttribute("location", 12, 12, address));
		issuerManagerEJBLocal.insert(demo1);

		User demo2 = new User();

		// simple attributes
		demo2.addAttribute(new StringAttribute("lastname", "Huber"));
		demo2.addAttribute(new StringAttribute("firstname", "Gerhard"));
		demo2.addAttribute(new DateAttribute("birthday", new GregorianCalendar(
				1975, 9, 15)));

		address = new Address("Elisabethstraße", "17", "Linz", "4020");
		demo2.addAttribute(new LocationAttribute("location", 12, 12, address));
		
		// competence attributes
		// driving licence
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/DrivingLicences/B"));

		// languages
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/Languages/E/3"));
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/Languages/G/1"));
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/Languages/F/2"));
		
		// social and personal
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/SocialResponsibility/Respect/1"));
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/SocialResponsibility/Helpfulness/1"));
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/SelfResponsibility/SelfReliance/1"));
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialPersonal/Leadership/Decisions/2"));
	
		// social work
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialWork/ChildCare/Dealing/1"));
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialWork/ChildCare/EmotionalCare/2"));
		demo2.addAttribute(new CompetenceAttribute("competence",
				"http://inloc.crac.at/competencies/v1/SocialWork/ChildCare/PhysicalDevelopment/2"));
		
		issuerManagerEJBLocal.insert(demo2);

		User demo3 = new User();

		// simple attributes
		demo3.addAttribute(new StringAttribute("lastname", "Keller"));
		demo3.addAttribute(new StringAttribute("firstname", "Maria"));
		demo3.addAttribute(new DateAttribute("birthday", new GregorianCalendar(
				1990, 11, 8)));

		address = new Address("Badgasse", "7", "Linz", "4020");
		demo3.addAttribute(new LocationAttribute("location", 12, 12, address));
		issuerManagerEJBLocal.insert(demo3);

		User demo4 = new User();

		// simple attributes
		demo4.addAttribute(new StringAttribute("lastname", "James"));
		demo4.addAttribute(new StringAttribute("firstname", "Marshall"));
		demo4.addAttribute(new DateAttribute("birthday", new GregorianCalendar(
				1985, 9, 13)));

		address = new Address("Prunerstraße", "18", "Linz", "4020");
		demo4.addAttribute(new LocationAttribute("location", 12, 12, address));
		issuerManagerEJBLocal.insert(demo4);

		User demo5 = new User();

		// simple attributes
		demo5.addAttribute(new StringAttribute("lastname", "Huber"));
		demo5.addAttribute(new StringAttribute("firstname", "Franz"));
		demo5.addAttribute(new DateAttribute("birthday", new GregorianCalendar(
				1963, 6, 23)));

		address = new Address("Hofgasse", "2", "Linz", "4020");
		demo5.addAttribute(new LocationAttribute("location", 12, 12, address));
		issuerManagerEJBLocal.insert(demo5);

		User demo6 = new User();

		// simple attributes
		demo6.addAttribute(new StringAttribute("lastname", "Stern"));
		demo6.addAttribute(new StringAttribute("firstname", "Gilbert"));
		demo6.addAttribute(new DateAttribute("birthday", new GregorianCalendar(
				1991, 7, 28)));

		address = new Address("Wimhölzelstraße", "2", "Linz", "4020");
		demo6.addAttribute(new LocationAttribute("location", 12, 12, address));
		issuerManagerEJBLocal.insert(demo6);

		User demo7 = new User();

		// simple attributes
		demo7.addAttribute(new StringAttribute("lastname", "Last"));
		demo7.addAttribute(new StringAttribute("firstname", "Barney"));
		demo7.addAttribute(new DateAttribute("birthday", new GregorianCalendar(
				1987, 5, 30)));

		address = new Address("Heindlstraße", "6", "Linz/Urfahr", "4040");
		demo7.addAttribute(new LocationAttribute("location", 12, 12, address));
		issuerManagerEJBLocal.insert(demo7);
		
		//insert has worked with relations
		demo.addHasWorkedWith(demo0);
		demo.addHasWorkedWith(demo1);
		demo.addHasWorkedWith(demo5);
		
		demo0.addHasWorkedWith(demo);
		demo0.addHasWorkedWith(demo1);
		demo0.addHasWorkedWith(demo7);
		
		demo1.addHasWorkedWith(demo);
		demo1.addHasWorkedWith(demo0);
		demo1.addHasWorkedWith(demo7);
		
		demo2.addHasWorkedWith(demo4);
		
		demo3.addHasWorkedWith(demo6);
		
		demo4.addHasWorkedWith(demo5);
		demo4.addHasWorkedWith(demo2);
		
		demo5.addHasWorkedWith(demo);
		demo5.addHasWorkedWith(demo4);
		
		demo6.addHasWorkedWith(demo3);
		
		demo7.addHasWorkedWith(demo0);
		demo7.addHasWorkedWith(demo1);
		
		//insert like to work with relations
		demo.addLikesToWorkWith(demo2);
		demo.addLikesToWorkWith(demo5);
		
		demo1.addLikesToWorkWith(demo6);
		
		demo3.addLikesToWorkWith(demo0);
		
		demo4.addLikesToWorkWith(demo7);
		
		demo5.addLikesToWorkWith(demo3);
		demo5.addLikesToWorkWith(demo4);
		
		demo6.addLikesToWorkWith(demo1);
		
		demo7.addLikesToWorkWith(demo6);
		
		//likes not to work with relations
		demo.addLikesNotToWorkWith(demo4);
		
		demo0.addLikesNotToWorkWith(demo1);
		
		demo2.addLikesNotToWorkWith(demo7);
		
		demo4.addLikesNotToWorkWith(demo);
		demo4.addLikesNotToWorkWith(demo5);
		
		demo5.addLikesNotToWorkWith(demo2);		
		
		demo6.addLikesNotToWorkWith(demo3);
		
		//update issuerManagerEJBLocal to persist all user connections with database
		issuerManagerEJBLocal.update();
	}
}
