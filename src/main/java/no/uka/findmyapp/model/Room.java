package no.uka.findmyapp.model;

/**
 * @author Cecilie Haugstvedt
 * @author Audun Sorheim
 */
public class Room {

	public String roomName;
	public int roomID;
	
	public Room() {
		super();
	}
	
	
	public Room(int roomID) {
		super();
		this.roomID = roomID;
	}


	public Room(String roomName, int roomID) {
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
	public int getRoomID() {
		return roomID;
	}
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	
	
}
