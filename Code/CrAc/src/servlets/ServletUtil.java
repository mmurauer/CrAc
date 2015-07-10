package servlets;

import inloc.LOCrel;
import inloc.LOCscheme;
import inloc.LOCstructure;
import inloc.LOCtypeLOC;

import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ServletUtil {

	static Calendar parseDateString(String date) {
		String[] dateArray = date.split("\\.");

		return new GregorianCalendar(Integer.parseInt(dateArray[2]),
				Integer.parseInt(dateArray[1]) - 1,
				Integer.parseInt(dateArray[0]));
	}
	
	static Calendar parseDateTimeString(String datetime) {
		
		String[] parts = datetime.split(" ");
		
		String[] dateArray = parts[0].split("\\.");
		String[] timeArray = parts[1].split(":");
		
		System.out.println(Integer.parseInt(
				timeArray[0]));
		System.out.println(Integer.parseInt(
				timeArray[1]));
		return new GregorianCalendar(Integer.parseInt(dateArray[2]),
				Integer.parseInt(dateArray[1]) - 1,
				Integer.parseInt(dateArray[0]), Integer.parseInt(timeArray[0]),
				Integer.parseInt(timeArray[1]));
	}
	
	static String parseCalendar(Calendar date) {
		return new SimpleDateFormat("dd.MM.yyyy").format(date.getTime());
	}
	static void buildXmlForm(LOCtypeLOC locType, Node node, BigDecimal i,
			String checkboxname, boolean printDate) {
		Element compnode = node.getOwnerDocument().createElement("li");

		Element span = compnode.getOwnerDocument().createElement("span");
		span.setTextContent(((i != BigDecimal.ZERO) ? i.toString() + ": " : "")
				+ locType.toString());
		compnode.appendChild(span);

		Element checkbox = compnode.getOwnerDocument().createElement("input");
		checkbox.setAttribute("type", "checkbox");
		checkbox.setAttribute("name", checkboxname + "_" + locType.getId());
		checkbox.setAttribute("value", locType.getId());
		compnode.appendChild(checkbox);

		if (printDate) {
			Element date = compnode.getOwnerDocument().createElement("input");
			date.setAttribute("class", "date");
			date.setAttribute("size", "12");
			date.setAttribute("placeholder", "Achieved on");
			date.setAttribute("name", checkboxname + "_" + locType.getId()
					+ "_date");
			compnode.appendChild(date);

			Element expireDate = compnode.getOwnerDocument().createElement(
					"input");
			expireDate.setAttribute("class", "date");
			expireDate.setAttribute("size", "12");
			expireDate.setAttribute("placeholder", "Expires on");
			expireDate.setAttribute("name",
					checkboxname + "_" + locType.getId() + "_expireDate");
			compnode.appendChild(expireDate);
		}

		node.appendChild(compnode);

		if (locType.getChildren(LOCscheme.hasPart).size() > 0) {
			Element ul = compnode.getOwnerDocument().createElement("ul");
			compnode.appendChild(ul);
			for (LOCrel current : locType.getChildren(LOCscheme.hasPart)) {
				buildXmlForm(current.getLocType(), ul, BigDecimal.ZERO,
						checkboxname, printDate);
			}
		}

		Element ul = null;

		if ((locType.getChildren(LOCscheme.hasDefinedLevel).size() > 0)
				|| (locType.getChildren(LOCscheme.hasExample).size() > 0)) {
			ul = compnode.getOwnerDocument().createElement("ul");
			compnode.appendChild(ul);
		}
		if (locType.getChildren(LOCscheme.hasDefinedLevel).size() > 0) {

			// if: LOCstructure with defined level = global level
			if (!(locType instanceof LOCstructure)) {
				Element levels_li = compnode.getOwnerDocument().createElement(
						"li");
				Element levels_span = compnode.getOwnerDocument()
						.createElement("span");
				levels_span.setTextContent("Levels");
				levels_li.appendChild(levels_span);

				ul.appendChild(levels_li);
				Element levels_ul = compnode.getOwnerDocument().createElement(
						"ul");
				levels_li.appendChild(levels_ul);

				for (LOCrel current : locType
						.getChildren(LOCscheme.hasDefinedLevel)) {

					buildXmlForm(current.getLocType(), levels_ul,
							current.getNumber(), checkboxname, printDate);
				}
			}
		}
		if (locType.getChildren(LOCscheme.hasExample).size() > 0) {
			Element examples_li = compnode.getOwnerDocument().createElement(
					"li");
			Element examples_span = compnode.getOwnerDocument().createElement(
					"span");
			examples_span.setTextContent("Examples");
			examples_li.appendChild(examples_span);

			ul.appendChild(examples_li);
			Element examples_ul = compnode.getOwnerDocument().createElement(
					"ul");
			examples_li.appendChild(examples_ul);

			for (LOCrel current : locType.getChildren(LOCscheme.hasExample)) {
				buildXmlForm(current.getLocType(), examples_ul,
						BigDecimal.ZERO, checkboxname, printDate);
			}
		}
	}
	
	static final String prettyPrint(Document xml) throws Exception {
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		Writer out = new StringWriter();
		tf.transform(new DOMSource(xml), new StreamResult(out));
		return out.toString();
	}
}
