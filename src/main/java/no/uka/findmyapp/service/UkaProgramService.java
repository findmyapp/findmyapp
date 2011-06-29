package no.uka.findmyapp.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.uka.findmyapp.datasource.UkaProgramRepository;
import no.uka.findmyapp.model.Event;
import no.uka.findmyapp.model.UkaProgram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UkaProgramService {
	@Autowired
	private UkaProgramRepository data;

	private static final int maxED = 5;//maximum item edit distance to include from titleSearch

	public static UkaProgram titleSearch(String qry) {

		if (qry.replace(" ", "").length() < 4) {//too short query return empty
			return new UkaProgram();
		}
		//todo: add all events in repo to prg

		//search for match:
		ArrayList<Event> prg = new ArrayList<Event>(); //for test
		ArrayList<Event> retPrg = new ArrayList<Event>();
		int index[] = new int[maxED]; //index for sorting

		int ED;
		for (int i = prg.size(); i >= 0; --i) {
			ED = EditDistanceService.splitDistance(prg.get(i).getTitle(), qry);
			if (ED < maxED) {
				retPrg.add(index[ED], prg.get(i));
				for (int j = ED; j < maxED; j++) {
					index[j]++;
				}
			}
		}
		return new UkaProgram(retPrg);
	} 


	public List<String> getUkaPlaces(){
		List<String> places;
		places = data.getUkaPlaces();
		return places;
	}

	public Event getUkaEventById(int id){
		Event event;
		event = data.getUkaEventById(id);
		return event;
	}

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
}
