package no.uka.findmyapp.test;

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
		signals.add(new Signal("Strossa", -20));
		signals.add(new Signal("Klubben", -5));
	}
}
