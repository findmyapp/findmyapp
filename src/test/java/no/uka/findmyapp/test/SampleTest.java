package no.uka.findmyapp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SampleTest {

	// mock data for data from database
	private List<Signal> dbSignals1, dbSignals2, dbSignals3;  
	private Signal s1 = new Signal("Selskapssiden", -20), s2 = new Signal("Bodegaen", -5), s3 = new Signal("Edgar", -30),
	s4 = new Signal("Selskapssiden",-20), s5 = new Signal("Bodegaen",-5), s6 = new Signal("Edgar", -35), 
	s7 = new Signal("Selskapssiden", -20), s8 = new Signal("Bodegaen", -15), s9 = new Signal("Edgar", -120);
	private Sample dbSample1, dbSample2, dbSample3; 	
	
	@Before
    public void setUp() throws Exception {
        // Code executed before each test   
		
		//create DB-data
		dbSignals1 = new ArrayList<Signal>();
		dbSignals2 = new ArrayList<Signal>();
		dbSignals3 = new ArrayList<Signal>();
		
		dbSignals1.add(s1);
		dbSignals1.add(s2);
		dbSignals1.add(s3);
		
		dbSignals2.add(s4);
		dbSignals2.add(s5);
		dbSignals2.add(s6);
		
		dbSignals3.add(s7);
		dbSignals3.add(s8);
		dbSignals3.add(s9);
		
		dbSample1 = new Sample(1,dbSignals1);
		dbSample2 = new Sample(2,dbSignals2);
		dbSample3 = new Sample(3,dbSignals3);

    }
 
    @After
    public void tearDown() throws Exception {
        // Code executed after each test   
    }
    
	@Test
	public void testGetDistance(){
		
		// Distance between identical lists should be 0 
		assertEquals(0, dbSample1.getDistance(dbSignals1), 0);
		
		// Distance when users list is different from list in DB but contain all the APs
		assertEquals(5, dbSample1.getDistance(dbSignals2), 0); //compares dbSignal1 with dbSignal2, should equal sqrt(90)
		
		// Distance when users list does not contain all the APs
		// Should use signalStrength of -120 for missing data
		List<Signal> signalList = new ArrayList<Signal>();
		signalList.add(s1);
		signalList.add(s2);
		assertEquals(90, dbSample1.getDistance(signalList), 0); //compares dbSignal1 with signalList
	}
	
}
