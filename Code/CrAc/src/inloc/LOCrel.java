package inloc;

import java.math.BigDecimal;

/**
 * 
 * This class is used to represent the relationship between to LOCtypes. The scheme defines the
 * type of the relationship, the supported types are listed in LOCscheme.java.
 * The locType defines the affected locType and the number attribute is used optional for the level.
 * 
 * @author Florian Jungwirth, Michaela Murauer
 */
public class LOCrel {

	private LOCscheme scheme;
	private LOCtypeLOC locType;
	private BigDecimal number;
	
	public LOCrel(LOCscheme scheme, LOCtypeLOC locType) {
		super();
		this.scheme = scheme;
		this.locType = locType;
	}
	public LOCrel(LOCscheme scheme, LOCtypeLOC locType, BigDecimal number) {
		super();
		this.scheme = scheme;
		this.locType = locType;
		this.number = number;
	}
	public LOCscheme getScheme() {
		return scheme;
	}
	public void setScheme(LOCscheme scheme) {
		this.scheme = scheme;
	}
	public LOCtypeLOC getLocType() {
		return locType;
	}
	public void setLocType(LOCtypeLOC locType) {
		this.locType = locType;
	}
	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((locType == null) ? 0 : locType.hashCode());
		result = prime * result + ((scheme == null) ? 0 : scheme.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LOCrel other = (LOCrel) obj;
		if (locType == null) {
			if (other.locType != null)
				return false;
		} else if (!locType.equals(other.locType))
			return false;
		if (scheme != other.scheme)
			return false;
		return true;
	}

	@Override
	public String toString() {
		//return "(" + scheme + ") " + locType;
		return locType.toString();
	}
	
}
