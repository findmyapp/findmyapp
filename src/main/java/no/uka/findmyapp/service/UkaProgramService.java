package no.uka.findmyapp.service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.uka.findmyapp.configuration.SearchConfiguration;
import no.uka.findmyapp.configuration.UkaProgramConfiguration;
import no.uka.findmyapp.configuration.UkaProgramConfigurationList;
import no.uka.findmyapp.datasource.UkaProgramRepository;

import no.uka.findmyapp.exception.UkaYearNotFoundException;

import no.uka.findmyapp.datasource.UserRepository;
import no.uka.findmyapp.model.Event;
import no.uka.findmyapp.model.UkaProgram;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.model.facebook.FacebookUserProfile;
import no.uka.findmyapp.service.helper.EditDistanceHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;


/**
 * This class is used between the UkaProgramController layer and the UkaProgramRepository layer. It contains most of the logic.
 */
@Service
public class UkaProgramService {
	
	@Autowired
	private UkaProgramRepository data;
			
	@Autowired
	private EditDistanceHelper edService;
	
	@Autowired
	private SearchConfiguration searchConfiguration;
	
	@Autowired
	private	UkaProgramConfigurationList ukaProgramConfigurationList;
	
	private static final Logger logger = LoggerFactory
	.getLogger(UkaProgramRepository.class);
	
	/**
	 * titleSearch searches for a list of events with names containing a substring close to the query.
	 * @param qry is the search query. 
	 * @return is a list of all the events sorted by relevance, wrapped inside UkaProgram. 
	 */
	public  UkaProgram titleSearch(String ukaYear, String qry) 
		throws UkaYearNotFoundException {
		
		UkaProgramConfiguration config = ukaProgramConfigurationList.get(ukaYear);
		if (config == null) {
			throw new UkaYearNotFoundException("ukaYear "+ukaYear+" not found ");
		}
		Date from = config.getStartDate();
		Date to = config.getEndDate();
		
		
		if (qry.replace(" ", "").length() < searchConfiguration.getMinLength()) {
			throw new IllegalArgumentException("Query \""+qry+"\" too short, min length is "+searchConfiguration.getMinLength());
		}
		ArrayList<Event> allEvents = (ArrayList<Event>) data.getUkaProgram(from, to); //for test
		ArrayList<Event> matchedEvents = new ArrayList<Event>();
		int index[] = new int[searchConfiguration.getDepth()]; //index for sorting

		int ED;
		for (int i = 0; i < allEvents.size(); i++) {
			ED = edService.splitDistance(allEvents.get(i).getTitle().toLowerCase(), qry.toLowerCase());
			if (ED < searchConfiguration.getDepth()) {
				matchedEvents.add(index[ED], allEvents.get(i));
				for (int j = ED; j < searchConfiguration.getDepth(); j++) {
					index[j]++;
				}
			}
		}
		return new UkaProgram(matchedEvents);
	}
	
	/**
	 * getUkaPlaces returns all places where there is one event occurring.
	 * @return list of places (identified by their names)
	 */
	public List<String> getUkaPlaces(String ukaYear)
		throws UkaYearNotFoundException {
		UkaProgramConfiguration config = ukaProgramConfigurationList.get(ukaYear);
		if (config == null) {
			throw new UkaYearNotFoundException("ukaYear "+ukaYear+" not found ");
		}
		Date from = config.getStartDate();
		Date to = config.getEndDate();
		return data.getUkaPlaces(from, to);
	}
	
	/**
	 * getNextUkaEvent returns the next event that takes place in the given input place
	 * @param place is the event place you want to ask for
	 * @return an event
	 */
	public Event getNextUkaEvent(String ukaYear, String place) 
		throws UkaYearNotFoundException {
		UkaProgramConfiguration config = ukaProgramConfigurationList.get(ukaYear);
		if (config == null) {
			throw new UkaYearNotFoundException("ukaYear "+ukaYear+" not found ");
		}
		Date from = config.getStartDate();
		Date to = config.getEndDate();
		return data.getNextUkaEvent(place, from, to);
	}
	
	/**
	 * getUkaEventById looks up an event by using the id in the database via a call to the class UkaProgramRepository.
	 * @param id is the database id (primary key). 
	 * @return is the event with the given id. If there is no such event then null is returned.
	 */

	public Event getUkaEventById(String ukaYear, int id)
		throws UkaYearNotFoundException {
		UkaProgramConfiguration config = ukaProgramConfigurationList.get(ukaYear);
		if (config == null) {
			throw new UkaYearNotFoundException("ukaYear "+ukaYear+" not found ");
		}
		Date from = config.getStartDate();
		Date to = config.getEndDate();
		return data.getUkaEventById(id, from, to);
	}

	/**
	 * getUkaProgram takes several optional arguments (arguments can be null). It calls functions in UkaProgramRepository. 
	 * @param date is used if you want the list of events for a given day. One day is from midnight to midnight. If date is given then from and to is ignored. 
	 * @param from is used if you want the list of events for a time interval starting at from ending at to. Input to is necessary.
	 * @param to (see from).
	 * @param all is used if you want the whole program. Then use all=true and no other input. This solution has been chosen to avoid some input errors in the REST api.
	 * @param place can be used alone or together with date or to/from. When place is specified only events occurring at this place are returned. 
	 * @return a list of events wrapped inside an instance of the UkaProgram class.
	 */
	public UkaProgram getUkaProgram(String ukaYear, Date date, Date from, Date to, String place)
		throws UkaYearNotFoundException {
		UkaProgramConfiguration config = ukaProgramConfigurationList.get(ukaYear);
		if (config == null) {
			throw new UkaYearNotFoundException("ukaYear "+ukaYear+" not found ");
		}
		if (date != null) {
			from = date;
			to = new Date(date.getTime()+86400000);// to =  (date+24h)
		}
		
		UkaProgram program = new UkaProgram();
		from = getFromDate(config, from);
		to = getToDate(config, to);

		if (place==null){
			program.setEvents(data.getUkaProgram(from, to));
		}
		else {
			program.setEvents(data.getUkaProgram(from, to, place));
		}
		return program;
	}
	/**
	 * Private method for getting the last date of arguments
	 * @param config 
	 * @param date
	 * @return
	 */
	private Date getFromDate(UkaProgramConfiguration config, Date date) {
		if (date == null || config.getStartDate().after(date)) {
			return config.getStartDate();
		}
		else {
			return date;
		}
	}
	/**
	 * Private method for getting the first date of arguments
	 * @param config 
	 * @param date
	 * @return
	 */
	private Date getToDate(UkaProgramConfiguration config, Date date) {
		if (date == null || config.getEndDate().before(date)) {
			return config.getEndDate();
		}
		else {
			return date;
		}
	}
	
	
	/**
	 * setUkaProgramRepository is only used for testing.
	 * @param repository
	 */
	public void setUkaProgramRepository(UkaProgramRepository repository) {
		this.data = repository;
	}

	public List<UkaProgramConfiguration> getUkaProgramConfiguration() {
		return ukaProgramConfigurationList.getList();
	}
	public UkaProgramConfiguration getUkaProgramConfiguration(String ukaYear) 
		throws UkaYearNotFoundException {
		UkaProgramConfiguration config = ukaProgramConfigurationList.get(ukaYear);
		if (config == null) {
			throw new UkaYearNotFoundException("ukaYear "+ukaYear+" not found ");
		}
		return config;
	}
	
	public void setUkaProgramConfigurationList(UkaProgramConfigurationList list) {
		this.ukaProgramConfigurationList = list;
		
	}

}
