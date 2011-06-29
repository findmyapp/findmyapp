package no.uka.findmyapp.model;

/**
 * @author Cecilie Haugstvedt
 * @author Audun Sorheim
 */
public class Room {

	public String roomName;
	public int roomId;
	
	public Room() {}
	
	public Room(int roomId) {
		this.roomId = roomId;
	}

	public Room(String roomName, int roomId) {
		this.roomName = roomName;
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
}
