package no.uka.findmyapp.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;

import org.junit.Test;

public class SampleTest {

	@Test
	public void testGetDistance(){
		
	}
	
	@Test
	public void testGetSignal(){
		Sample sample = new Sample();
		List<Signal> signals = new ArrayList<Signal>();
		Signal s1 = new Signal("Strossa", -20);
		signals.add(s1);
		signals.add(new Signal("Klubben", -5));
		sample.setRoomID(4);
		sample.setSignalList(signals);
		assertEquals("", s1, sample.getSignal("Strossa"));
	}
}
