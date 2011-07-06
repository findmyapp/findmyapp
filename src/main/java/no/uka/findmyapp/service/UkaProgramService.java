package no.uka.findmyapp.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.uka.findmyapp.datasource.UkaProgramRepository;
import no.uka.findmyapp.model.Event;
import no.uka.findmyapp.model.UkaProgram;
import no.uka.findmyapp.service.helper.EditDistanceHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * This class is used between the UkaProgramController layer and the UkaProgramRepository layer. It contains most of the logic.
 */
@Service
public class UkaProgramService {
	@Autowired
	private UkaProgramRepository data;
	@Autowired
	private EditDistanceHelper edService;

	private static final int maxED = 3;//maximum item edit distance to include from titleSearch
	
	/**
	 * titleSearch searches for a list of events with names containing a substring close to the query.
	 * @param qry is the search query. 
	 * @return is a list of all the events sorted by relevance, wrapped inside UkaProgram. 
	 */
	public  UkaProgram titleSearch(String qry) {

		if (qry.replace(" ", "").length() < 6) {//too short query return empty
			return new UkaProgram();
		}
		//todo: add all events in repo to prg

		//search for match:
		ArrayList<Event> prg = (ArrayList<Event>) data.getUkaProgram(); //for test
		ArrayList<Event> retPrg = new ArrayList<Event>();
		int index[] = new int[maxED]; //index for sorting

		int ED;
		for (int i = 0; i < prg.size(); i++) {
			ED = edService.splitDistance(prg.get(i).getTitle(), qry);
			if (ED < maxED) {
				retPrg.add(index[ED], prg.get(i));
				for (int j = ED; j < maxED; j++) {
					index[j]++;
				}
			}
		}
		return new UkaProgram(retPrg);
	} 

	/**
	 * getUkaPlaces returns all places where there is one event occurring.
	 * @return list of places (identified by their names)
	 */
	public List<String> getUkaPlaces(){
		List<String> places;
		places = data.getUkaPlaces();
		return places;
	}
	
	/**
	 * getUkaEventById looks up an event by using the id in the database via a call to the class UkaProgramRepository.
	 * @param id is the database id (primary key). 
	 * @return is the event with the given id. If there is no such event then null is returned.
	 */
	public Event getUkaEventById(int id){
		Event event;
		event = data.getUkaEventById(id);
		return event;
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
	public UkaProgram getUkaProgram(Date date, Date from, Date to, Boolean all, String place){
		UkaProgram program = new UkaProgram();

		if (place==null){
			if (date!=null) {			
				// Use date
				List<Event> eventList = data.getUkaProgram(date);
				program = new UkaProgram(eventList);

			}else if(from != null && to != null) {
				// Use from to
				List<Event> eventList = data.getUkaProgram(from, to);
				program = new UkaProgram(eventList);


			}else if(all != null && all) {
				List<Event> eventList = data.getUkaProgram();
				program = new UkaProgram(eventList);

			}else{
				throw new IllegalArgumentException("Requesten må inneholde fra og tildato, en bestemt dato eller flag for alle events");
			}
		}

		else {

			if (date!=null) {			
				// Use date
				List<Event> eventList = data.getUkaProgram(date, place);
				program = new UkaProgram(eventList);

			}else if(from != null && to != null) {
				// Use from to
				List<Event> eventList = data.getUkaProgram(from, to, place);
				program = new UkaProgram(eventList);

			}else {
				List<Event> eventList = data.getUkaProgram(place);
				program = new UkaProgram(eventList);
			}
		}
		return program;
	}
	
	/**
	 * setUkaProgramRepository is only used for testing.
	 * @param repository
	 */
	public void setUkaProgramRepository(UkaProgramRepository repository) {
		this.data = repository;
	}
}
