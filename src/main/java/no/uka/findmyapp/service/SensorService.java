package no.uka.findmyapp.service;

import org.springframework.stereotype.Service;

/**
 * Jeg er ikke helt sikker, men jeg tror at vi kan lage logikk for aa regne paa dataene fra arduinoen her. 
 * Lagde den fordi det saa  ut som om den ble nadvendig.
 * 
 * @author John Modin
 * 
 */

@Service
public class SensorService {
	public float toDecibel(int value){
		return (float) 111.111;// Dummy method, will calculate decibel
	}
	

}
