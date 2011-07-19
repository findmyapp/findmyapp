package no.uka.findmyapp.model;

public class LocationReport {

		private	String comment;
		private float funFactor;
		private float danceFactor;
		private float flirtFactor;
		private float chatFactor;
		private float headCount;
		
		public LocationReport(){//Setting default values upon construction
			this.funFactor = -1;
			this.danceFactor = -1;
			this.flirtFactor = -1;
			this.chatFactor = -1;
			this.headCount = -1;
					
		}
		public void setComment(String comment){
			this.comment = comment;
		}
		
		public String getComment(){
			return this.comment;
		}
	
		public void setFunFactor(float ff){
			this.funFactor = ff;
		}
		
		public float getFunFactor (){
			return this.funFactor;
		}
		public void setDanceFactor(float df){
			this.danceFactor = df;
		}
		public float getDanceFactor (){
			return this.danceFactor;
		}
		public void setFlirtFactor(float ff){
			this.flirtFactor = ff;
		}
		public float getFlirtFactor (){
			return this.flirtFactor;
		}
		public void setChatFactor(float cf){
			this.chatFactor = cf;
		}
		public float getChatFactor (){
			return this.chatFactor;
		}
		public void setHeadCount(float hc){
			this.headCount = hc;
		}
		public float getHeadCount(){
			return this.headCount;
		}
		
}
