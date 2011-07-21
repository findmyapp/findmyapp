package no.uka.findmyapp.model;

import java.util.ArrayList;
import java.util.List;

public class UkaProgram {
	private List<UkaEvent> events;
	
	
	public UkaProgram() {
		events = new ArrayList<UkaEvent>();
	}
	public UkaProgram(List<UkaEvent> eventList) {
		this.events = eventList;
	}
	public void addEvent(UkaEvent e) {
		events.add(e);
	}
	public void removeEvent(UkaEvent e) {
		events.remove(e);
	}
	public void removeEvent(int i) {
		events.remove(i);
	}
	public int getEventSize() {
		return events.size();
	}
	public UkaEvent getEvent(int i) {
		return events.get(i);
	}
	public List<UkaEvent> getEvents() {
		return events;
	}
	public void setEvents(List<UkaEvent> events) {
		this.events = events;
	}
}
