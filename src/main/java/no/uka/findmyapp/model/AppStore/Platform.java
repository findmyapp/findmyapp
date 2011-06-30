package no.uka.findmyapp.model.AppStore;

public enum Platform {
	IOS(1), ANDROID(2);
	
    private int value;
	 
    private Platform(int intValue) {
    	this.value = intValue;
    }

    public int getValue() {
    	 return value;
    }

};
