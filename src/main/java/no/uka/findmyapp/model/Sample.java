package no.uka.findmyapp.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;

/** Class for test points
 * 
 * @author Cecilie Haugstvedt
 *
 */
@JsonAutoDetect
public class Sample {
	
	private int Id;
	private int locationId;
	private String locationName;
	private List<Signal> signalList;
		
	public Sample() {
		signalList = new ArrayList<Signal>();
	}
	
	public int getId() {
		return Id;
	}

	public void setId(int Id) {
		this.Id = Id;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int LocationId) {
		this.locationId = LocationId;
	}
	
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String LocationName) {
		this.locationName = LocationName;
	}
	
	public List<Signal> getSignalList() {
		return signalList;
	}

	public void setSignalList(List<Signal> signalList) {
		this.signalList = signalList;
	}
}
