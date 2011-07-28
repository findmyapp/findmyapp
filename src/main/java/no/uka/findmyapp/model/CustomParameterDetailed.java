package no.uka.findmyapp.model;


public class CustomParameterDetailed extends CustomParameter {
	private int entryCount;
	
	public CustomParameterDetailed() {
		super();
	}

	public int getEntryCount() {
		return entryCount;
	}

	public void setEntryCount(int entryCount) {
		this.entryCount = entryCount;
	}

	@Override
	public String toString() {
		return super.toString() + "CustomParameterDetails [entryCount=" + entryCount + "]";
	}
}
