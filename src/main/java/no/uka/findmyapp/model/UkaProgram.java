package no.uka.findmyapp.model;

import java.util.List;
import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

public class UkaProgram {
	private List<Event> eventList;
	
	public UkaProgram() {
		eventList = new ArrayList<Event>();
	}
	public UkaProgram(List<Event> eventList) {
		this.eventList = eventList;
	}
	public void addEvent(Event e) {
		eventList.add(e);
	}
	public void removeEvent(Event e) {
		eventList.remove(e);
	}
	public void removeEvent(int i) {
		eventList.remove(i);
	}
	public int getEventSize() {
		return eventList.size();
	}
	public Event getEvent(int i) {
		return eventList.get(i);
	}
	public List<Event> getEvents() {
		return eventList;
	}

	
}
