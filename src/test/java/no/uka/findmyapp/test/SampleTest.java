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
		Sample sample = new Sample();
		List<Signal> signals = new ArrayList<Signal>();
		Signal s1 = new Signal("Strossa", -20), s2 = new Signal("Klubben", -5);
		signals.add(s1);
		signals.add(s2);
		sample.setRoomID(4);
		sample.setSignalList(signals);
		
		List<Signal> signals2 = new ArrayList<Signal>();
		Signal s3 = new Signal("Storsalen", -10);
		signals.add(s1);
		signals.add(s2);
		
		assertEquals(0, sample.getDistance(signals2), 0);
		signals2.add(s3);
		assertEquals(1000, sample.getDistance(signals2), 0);
	}
	
	@Test
	public void testGetSignal(){
		Sample sample = new Sample();
		List<Signal> signals = new ArrayList<Signal>();
		Signal s1 = new Signal("Strossa", -20), s2 = new Signal("Klubben", -5);
		signals.add(s1);
		signals.add(s2);
		sample.setRoomID(4);
		sample.setSignalList(signals);
		assertEquals("", s1, sample.getSignal("Strossa"));
	}
}
