package no.uka.findmyapp.service;

import java.util.List;

import no.uka.findmyapp.datasource.LocationRepository;
import no.uka.findmyapp.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
	
	@Autowired
	private LocationRepository data;

	public List<User> getUsersAtLocation(int locationId) {
		return data.getUsersAtLocation(locationId);
	}

}
