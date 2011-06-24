package no.uka.findmyapp.model;

import java.util.List;
import java.util.ArrayList;

public class UkaProgram {
	private List<Event> events;
	private int eventSize;
	
	public UkaProgram() {
		events = new ArrayList<Event>();
		eventSize = 0;
	}
	public UkaProgram(List<Event> eventList) {
		this.events = eventList;
		eventSize = eventList.size();
	}
	public void addEvent(Event e) {
		events.add(e);
	}
	public void removeEvent(Event e) {
		events.remove(e);
	}
	public void removeEvent(int i) {
		events.remove(i);
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
