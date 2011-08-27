package no.uka.findmyapp.service;

import java.util.Date;
import java.util.List;

import no.uka.findmyapp.datasource.SensorRepository;
import no.uka.findmyapp.model.BeerTap;
import no.uka.findmyapp.model.Humidity;
import no.uka.findmyapp.model.Noise;
import no.uka.findmyapp.model.Temperature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class SensorService {
	@Autowired
	private SensorRepository data;
	

	public float toDecibel(int value) {
		return (float) 111.111;// Dummy method, will calculate decibel
	}
	
	public List<Temperature> getLatestTemperatureData(int location, int limit) {
		return data.getLatestTemperatureData(location, limit);
	}
	
	public List<Noise> getLatestNoiseData(int location, int limit) {
		return data.getLatestNoiseData(location, limit);
	}


	public List<Temperature> getTemperatureData(Date from, Date to, int location) {
		List<Temperature> temp = null;

		if (from == null && to == null) {
			temp = data.getTemperatureData(location);
		} else if (from != null && to == null) {
			temp = data.getTemperatureData(from, location);
		}

		else if (to != null && from == null) {
			temp = data.getTemperatureDataTo(to, location);

		} else if (from != null && to != null) {
			temp = data.getTemperatureData(from, to, location);
		}

		return temp;
	}

	public List<Noise> getNoiseData(Date from, Date to, int location) {
		List<Noise> noise = null;

		if (from == null && to == null) {
			noise = data.getNoiseData(location);
		} else if (from != null && to == null) {
			noise = data.getNoiseData(from, location);
		}

		else if (to != null && from == null) {
			noise = data.getNoiseDataTo(to, location);

		} else if (from != null && to != null) {
			noise = data.getNoiseData(from, to, location);
		}

		return noise;
	}

	public List<Humidity> getHumidityData(Date from, Date to, int location) {
		List<Humidity> hum = null;

		if (from == null && to == null) {
			hum = data.getHumidityData(location);
		} else if (from != null && to == null) {
			hum = data.getHumidityData(from, location);
		}

		else if (to != null && from == null) {
			hum = data.getHumidityDataTo(to, location);

		} else if (from != null && to != null) {
			hum = data.getHumidityData(from, to, location);
		}

		return hum;
	}
	
	public List<Humidity> getLatestHumidityData(int location, int limit) {	
		return data.getLatestHumidityData(location, limit);
	}
	

	public List<BeerTap> getBeertapData(int locationId, int tapnr, Date from,
			Date to) {
		if (from != null && to != null) {
			return data.getBeertapData(locationId, tapnr, from, to);
		} else {
			return data.getBeertapData(locationId, tapnr);
		}
	}
	
	public List<BeerTap> getLatestBeerTapData(int location, int tapNr, int limit) {	
		return data.getLatestBeerTapData(location, tapNr, limit);
	}

	public int getBeertapSum(int locationId, int tapnr, Date from, Date to) {
		if (from != null && to != null) {
			return data.getBeertapSum(locationId, tapnr, from, to);
		} else {
			return data.getBeertapSum(locationId, tapnr);
		}
	}

	public boolean setTemperatureData(int locationId, float value) {
		
		return data.setTemperatureData(locationId, value);
	}

	public boolean setHumidityData(int locationId, float value) {

		
		return data.setHumidityData(locationId, value);
	}

	public boolean setBeertapData(int locationId, float value, int tapnr) {

		return data.setBeertapData(locationId, value, tapnr);
	}
	
	public boolean setNoiseData(int locationId, int[] sample) {

		// returns Noise data.
		Noise noise = extractNoiseData(locationId, sample);
		data.setNoiseData(noise); 
		return true; // 
	}

	private Noise extractNoiseData(int locationId, int[] samples) {
		Noise noise = new Noise();
		noise.setLocation(locationId);

		noise.setAverage(calcAverage(samples));
		noise.setMax(calcMax(samples));
		noise.setMin(calcMin(samples));
		noise.setStandardDeviation(calcStandardDeviation(samples));
		noise.setSamples(samples);
		
		return noise;

	}

	private double calcAverage(int[] samples) {
	 
		int sum = 0;
	 
		for(int i=0; i < samples.length ; i++)
			sum = sum + samples[i];
	 
			//calculate average value
		double average = sum / samples.length;
	 
		return average;
		
	}
	
	private double calcStandardDeviation(int[] samples){
		double average = calcAverage(samples);
		float sum = 0;
		double deviation = 0;
		
		for (int i = 0; i < samples.length; i++) {
			sum += Math.pow((samples[i] - average), 2);
		}
			
		deviation = Math.sqrt(sum /10); 
			
		return deviation;
	}
	
	private int calcMax(int[] samples){
		int max = 0;
		
		for (int i: samples) {
			if(i > max) max = i;
		}
		return max;
	}
	
	private int calcMin(int[] samples){
		int min = Integer.MAX_VALUE;
		for(int i: samples){
			if(i<min) min = i;
		}
		return min;
	}


	

}
