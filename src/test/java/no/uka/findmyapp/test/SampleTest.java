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

	private Sample sample;
	private List<Signal> signals;
	private Signal s1 = new Signal("Strossa", -20), s2 = new Signal("Klubben", -5);
	
	@Before
    public void setUp() throws Exception {
        // Code executed before each test   
		sample = new Sample();
		signals = new ArrayList<Signal>();
		signals.add(s1);
		signals.add(s2);
		sample.setRoomId(4);
		sample.setSignalList(signals); 
    }
 
    @After
    public void tearDown() throws Exception {
        // Code executed after each test   
    }
    
	@Test
	public void testGetDistance(){
		
		List<Signal> signals2 = new ArrayList<Signal>();
		signals2.add(s1);
		signals2.add(s2);
		
		// Distance between identical lists should be 0 
		assertEquals(0, sample.getDistance(signals2), 0);
		
		//What should the distance be if we cannot see an access point?
		Signal s3 = new Signal("Storsalen", -10);
		signals2.add(s3);
//		assertEquals(1000, sample.getDistance(signals2), 0);
		
		signals2.remove(2);
		signals2.remove(1);
		Signal s4 = new Signal("Klubben", -15);
		signals2.add(s4);
		assertEquals(10, sample.getDistance(signals2), 0);
	}
	
	@Test
	public void testGetSignal(){
		assertEquals("", s1, sample.getSignal("Strossa"));
		assertNull(sample.getSignal("R1"));
	}
}
