package no.uka.findmyapp.service;

import java.sql.Timestamp;
import java.util.List;

import no.uka.findmyapp.model.Temperature;

import org.springframework.stereotype.Service;



@Service
public class SensorService {
	
	public float toDecibel(int value){
		return (float) 111.111;// Dummy method, will calculate decibel
	}
	

	public List<Temperature> getTemperatureData(Timestamp from, Timestamp to, String location){
		List<Temperature> temp = null;  // HER ER DET IKKE FERDIG
		
		return temp;
		}
}
