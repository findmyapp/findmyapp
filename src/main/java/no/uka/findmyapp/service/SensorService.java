package no.uka.findmyapp.service;

import java.util.Date;
import java.util.List;

import no.uka.findmyapp.datasource.SensorRepository;
import no.uka.findmyapp.model.Temperature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class SensorService {
	@Autowired
	private  SensorRepository data;
	
	public float toDecibel(int value){
		return (float) 111.111;// Dummy method, will calculate decibel
	}
	

	public List<Temperature> getTemperatureData(Date from, Date to, String location){
		List<Temperature> temp = null;	
		
		if(from == null && to == null){
			temp=data.getTemperatureData(location);
		}
		else if(from != null && to == null){
			temp=data.getTemperatureData(from,location);
		}
		
		else if (to != null && from == null){
			temp=data.getTemperatureDataTo(to,location);
		
		}
		else if(from !=null && to!=null){
			temp=data.getTemperatureData(from, to, location);
		}
		
		return temp;
	}
}

