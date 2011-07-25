package no.uka.findmyapp.model;

public class LocationReport {
	private String parameterName;//Name of the parameter, i.e. fun_factor or comment
	private String parameterTextValue; 
	private float parameterNumberValue;
		
	public LocationReport(){
		parameterNumberValue = -1;
	}
	public String getParameterName(){
		return parameterName;
	}
	public void setParameterName(String name){
		this.parameterName = name;
	}
	public void setParameterTextValue(String text){
		this.parameterTextValue = text;
	}
	public String getParameterTextValue(){
		return parameterTextValue;
	}
	public float getParameterNumberValue(){
		return parameterNumberValue;
	}
	public void setParameterNumberValue(float num){
		this.parameterNumberValue = num;
	}
}
