package no.uka.findmyapp.service;

import java.util.Date;

import no.uka.findmyapp.datasource.UkaProgramRepository;

import org.junit.Test;
import org.mockito.Mockito;

public class UkaProgramServiceTest {

	@Test
	public void testMedKunDatoSomInput(){
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		Date now = new Date();
		service.getUkaProgram(now, null, null, null, null);
		Mockito.verify(mock).getUkaProgram(now);
	}
	
	@Test
	public void testMedKunPlaceSomInput(){
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		String place = "samfundet";
		service.getUkaProgram(null, null, null, false, place);
		Mockito.verify(mock).getUkaProgram(place);
	}
	
	
	
}
