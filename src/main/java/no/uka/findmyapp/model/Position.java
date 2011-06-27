package no.uka.findmyapp.model;

/**
 * @author Cecilie Haugstvedt
 * @author Audun Sorheim
 */
public class Position {

	public String roomName;
	public String roomID;
	
	public Position() {
		super();
	}
	
	public Position(String roomName, String roomID) {
		super();
		this.roomName = roomName;
		this.roomID = roomID;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRoomID() {
		return roomID;
	}
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}
	
	
}
