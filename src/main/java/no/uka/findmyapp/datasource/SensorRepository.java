package no.uka.findmyapp.datasource;

import javax.sql.DataSource;

import no.uka.findmyapp.model.Arduino;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Enn så lenge en dummyklasse for å kunne bygge controlleren
 * 
 * @author John Modin
 * 
 */
@Repository
public class SensorRepository {
	@Autowired
	private DataSource ds;

	private static final Logger logger = LoggerFactory
	.getLogger(SensorRepository.class);
	
	public Arduino getSensorData(){//Dummy metode for koding av Controller
		Arduino ad = new Arduino();
		ad.setSensor("Her skal sensor stå");
		ad.setLocation("Her skal location stå");
		ad.setValue(0);
		
		return ad;
	}
}
