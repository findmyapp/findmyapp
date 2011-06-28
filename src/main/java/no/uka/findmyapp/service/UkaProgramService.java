package no.uka.findmyapp.service;
import java.util.ArrayList;

import no.uka.findmyapp.datasource.UkaProgramRepository;
import no.uka.findmyapp.model.UkaProgram;
import no.uka.findmyapp.model.Event;

 

public class UkaProgramService {
	private static final int maxED = 5;//maximum item edit distance to include from titleSearch
	
	public static UkaProgram titleSearch(String qry) {
		//add all events in repo to prg
		
		//search for match:
		if (qry.replace(" ", "").length() < 4) {//too short query return empty
			return new UkaProgram();
		}
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
}
