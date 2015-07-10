package inloc;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * This is the key class for managing the loc definitions and structures. It reads in the
 * competence and interest xml files and creates internal loc tree strucutres, which then can be
 * accessed for gaining any required inloc information.
 * 
 * @author Florian Jungwirth, Michaela Murauer
 */
@SuppressWarnings("unchecked")
public class LOCUtil {

	private static ArrayList<LOCstructure> locStructuresCompetencies;
	private static ArrayList<LOCstructure> locStructuresInterests;

	static {
		locStructuresCompetencies = new ArrayList<LOCstructure>();
		locStructuresInterests = new ArrayList<LOCstructure>();

		String packageName = LOCstructure.class.getPackage().getName();
		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance(packageName);

			Unmarshaller u = jc.createUnmarshaller();
			
			URL res = LOCUtil.class.getResource("competencies/");
			File folder = new File(res.getFile());
			
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".xml")) {

					JAXBElement<LOCstructure> doc;
					doc = (JAXBElement<LOCstructure>) u
							.unmarshal(listOfFiles[i]);
					LOCstructure locStructure = doc.getValue();

					locStructure.buildLOCtree();

					locStructuresCompetencies.add(locStructure);
				}
			}
			
			res = LOCUtil.class.getResource("interests");
			folder = new File(res.getFile());
			listOfFiles = folder.listFiles();
			
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {

					JAXBElement<LOCstructure> doc;
					doc = (JAXBElement<LOCstructure>) u
							.unmarshal(listOfFiles[i]);
					LOCstructure locStructure = doc.getValue();

					locStructure.buildLOCtree();

					locStructuresInterests.add(locStructure);
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<LOCstructure> getLocStructuresCompetencies() {
		return locStructuresCompetencies;
	}
	
	public static ArrayList<LOCstructure> getLocStructuresInterests() {
		return locStructuresInterests;
	}

	private static LOCdefinition getLOCdefinitionById(List<LOCstructure> locStructures, String Id){
		for(LOCstructure struct : locStructures){
			if(struct.getLOCdefinitionById(Id) != null)
				return struct.getLOCdefinitionById(Id);
		}
		return null;
	}
	public static LOCdefinition getLOCdefinitionByInterestId(String Id){
		return getLOCdefinitionById(locStructuresInterests, Id);
	}
	public static LOCdefinition getLOCdefinitionByCompetenceId(String Id){
		return getLOCdefinitionById(locStructuresCompetencies, Id);
	}
}
