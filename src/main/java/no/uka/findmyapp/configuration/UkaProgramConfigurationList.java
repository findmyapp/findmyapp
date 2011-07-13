package no.uka.findmyapp.configuration;

import java.util.List;

public class UkaProgramConfigurationList {
	private List<UkaProgramConfiguration> list;
	
	public List<UkaProgramConfiguration> getList() {
		return list;
	}
	public void setList(List<UkaProgramConfiguration> list) {
		this.list = list;
	}
	public UkaProgramConfiguration get(String key) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getUkaYear().equalsIgnoreCase(key)) {
				return list.get(i);
			}
		}
		return null;
	}
}
