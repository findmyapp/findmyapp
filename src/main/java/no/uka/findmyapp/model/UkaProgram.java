package no.uka.findmyapp.model;

import java.util.ArrayList;
import java.util.List;

public class UkaProgram {
	private List<Event> events;
	private int eventSize;
	
	
	public UkaProgram() {
		events = new ArrayList<Event>();
		eventSize = 0;
	}
	public UkaProgram(List<Event> eventList) {
		this.events = eventList;
		eventSize = events.size();
	}
	public void addEvent(Event e) {
		events.add(e);
		eventSize = events.size();
	}
	public void removeEvent(Event e) {
		events.remove(e);
		eventSize = events.size();
	}
	public void removeEvent(int i) {
		events.remove(i);
		eventSize = events.size();
	}
	public int getEventSize() {
		return events.size();
	}
	public Event getEvent(int i) {
		return events.get(i);
	}
	public List<Event> getEvents() {
		return events;
	}
}
