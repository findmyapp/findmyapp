package no.uka.findmyapp.model;

/**
 * @author Cecilie Haugstvedt
 * @author Audun Sorheim
 */
public class Room {

	public String roomName;
	public String roomID;
	
	public Room() {
		super();
	}
	
	
	public Room(String roomID) {
		super();
		this.roomID = roomID;
	}


	public Room(String roomName, String roomID) {
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
