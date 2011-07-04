package no.uka.findmyapp.model;

public class UserPosition {

		private String userId;
		private Room room;
		
		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}
		
		public Room getRoom() {
			return room;
		}
		
		public void setRoom(Room room) {
			this.room = room;
		}
}
