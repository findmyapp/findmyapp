package no.uka.findmyapp.model;

public class CustomParameter {
	protected int customParameterId;
	protected int appstoreDeveloperId;
	protected String parameterName;
	
	public int getCustomParameterId() {
		return customParameterId;
	}
	public void setCustomParameterId(int customParameterId) {
		this.customParameterId = customParameterId;
	}
	public int getAppstoreDeveloperId() {
		return appstoreDeveloperId;
	}
	public void setAppstoreDeveloperId(int appstoreDeveloperId) {
		this.appstoreDeveloperId = appstoreDeveloperId;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
}
