package no.uka.findmyapp.model.spotify;

import no.uka.findmyapp.model.Location;

public class MusicSession extends Location {
	private String sessionName;
	private boolean open;

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
}
