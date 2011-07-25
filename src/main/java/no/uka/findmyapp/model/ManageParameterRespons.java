package no.uka.findmyapp.model;

import java.util.ArrayList;
import java.util.List;

public class ManageParameterRespons {
	private String respons;
	private String suggestion;
	private String exception;
	private int numberOfRowsAffected;
	private boolean status;
	
	
	public ManageParameterRespons(){
		status = false;// Default values
		
	}
	
	public ManageParameterRespons(String respons, String suggestion, String exception){
		this.respons=respons;
		this.suggestion = suggestion;
		this.exception = exception;
		status = false;// Default values
		
	}
	public String getRespons(){
		return respons;
	}
	
	public String getSuggestion(){
		return suggestion;
	}
	
	public String getException(){
		return exception;
	}
	
	public int getNumberOfRowsAffected(){
		return numberOfRowsAffected;
	}
	public void setException(String exception){
		this.exception = exception;
	}
	public void setRespons(String respons){
		this.respons = respons;
	}
	public void setSuggestion(String suggestion){
		this.suggestion = suggestion;
	}
	public void setNumberOfRowsAffected(int nora){
		this.numberOfRowsAffected = nora;
	}
	public void setStatus(boolean b){
		status = b;
	}
	public boolean getStatus(){
		return status;
	}
	
}