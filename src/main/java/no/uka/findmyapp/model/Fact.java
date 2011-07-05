package no.uka.findmyapp.model;

/**
 * 
 * @author Cecilie Haugstvedt
 *
 */
public class Fact {

	private int factId;
	private int roomId;
	private String text;
	
	public Fact() {
	}

	public Fact(int roomId, String text) {
		this.roomId = roomId;
		this.text = text;
	}

	public int getFactId() {
		return factId;
	}

	public void setFactId(int factId) {
		this.factId = factId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
	
	
}
