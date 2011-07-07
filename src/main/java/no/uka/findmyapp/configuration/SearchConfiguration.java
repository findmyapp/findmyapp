package no.uka.findmyapp.configuration;

/**
 * Used to hold properties. Populated as bean in root-context.xml
 * 
 * @author kare.blakstad
 *
 */
public class SearchConfiguration {

	/**
	 * Default values
	 */
	int minLength = 1;
	int depth = 1;

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

}
