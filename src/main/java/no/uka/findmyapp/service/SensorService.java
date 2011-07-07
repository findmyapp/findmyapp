package no.uka.findmyapp.service;

import java.util.Date;
import java.util.List;

import no.uka.findmyapp.datasource.SensorRepository;
import no.uka.findmyapp.model.Beertap;
import no.uka.findmyapp.model.Humidity;
import no.uka.findmyapp.model.Noise;
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
	

	public List<Temperature> getTemperatureData(Date from, Date to, int location){
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
	
	public List<Noise> getNoiseData(Date from, Date to,int location){
		List<Noise> noise = null;	
		
		if(from == null && to == null){
			noise=data.getNoiseData(location);
		}
		else if(from != null && to == null){
			noise=data.getNoiseData(from,location);
		}
		
		else if (to != null && from == null){
			noise=data.getNoiseDataTo(to,location);
		
		}
		else if(from !=null && to!=null){
			noise=data.getNoiseData(from, to, location);
		}
		
		return noise;
	}
	
	public List<Humidity> getHumidityData(Date from, Date to, int location){
		List<Humidity> hum = null;	
		
		if(from == null && to == null){
			hum=data.getHumidityData(location);
		}
		else if(from != null && to == null){
			hum=data.getHumidityData(from,location);
		}
		
		else if (to != null && from == null){
			hum=data.getHumidityDataTo(to,location);
		
		}
		else if(from !=null && to!=null){
			hum=data.getHumidityData(from, to, location);
		}
		
		return hum;
	}
	
	public List<Beertap> getBeertapData(int locationId,int tapnr){
		 return data.getBeertapData(locationId,tapnr);
	}
	
	public List<Beertap> getBeertapData(int locationId,int tapnr, Date from, Date to){
		 return data.getBeertapData(locationId,tapnr, from, to);
	}
	
	public int getBeertapSum(int locationId,int tapnr){
		 return data.getBeertapSum(locationId,tapnr);
	}
	
	public int getBeertapSum(int locationId,int tapnr, Date from, Date to){
		 return data.getBeertapSum(locationId,tapnr, from, to);
	}
}

