package inloc;

/**
 * This enum lists the supported InLOC relationships
 * 
 * @author Florian Jungwirth, Michaela Murauer
 */
public enum LOCscheme {

	hasPart("http://purl.org/net/inloc/hasLOCpart"),
	isPartOf("http://purl.org/net/inloc/isLOCpartOf"),
	hasExample("http://purl.org/net/inloc/hasExample"),
	isExampleOf("http://purl.org/net/inloc/isExampleOf"),
	hasDefinedLevel("http://purl.org/net/inloc/hasDefinedLevel"),
	isDefinedLevelOf("http://purl.org/net/inloc/isDefinedLevelOf");

	private final String text;

	private LOCscheme(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
	
	
}
