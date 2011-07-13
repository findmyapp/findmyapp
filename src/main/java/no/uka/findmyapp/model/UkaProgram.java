package no.uka.findmyapp.model;

import java.util.ArrayList;
import java.util.List;

public class UkaProgram {
	private List<Event> events;
	
	
	public UkaProgram() {
		events = new ArrayList<Event>();
	}
	public UkaProgram(List<Event> eventList) {
		this.events = eventList;
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
	public void setEvents(List<Event> events) {
		this.events = events;
	}
}
