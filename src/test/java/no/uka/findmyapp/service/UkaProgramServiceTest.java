package no.uka.findmyapp.service;

import java.util.Date;

import no.uka.findmyapp.datasource.UkaProgramRepository;

import org.junit.Test;
import org.mockito.Mockito;

public class UkaProgramServiceTest {

	@Test
	public void testWithOnlyDateAsInput(){
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		Date now = new Date();
		service.getUkaProgram(now, null, null, null, null);
		Mockito.verify(mock).getUkaProgram(now);
	}
	
	@Test
	public void testWithDateFromAndTOAsInput(){
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		Date from = new Date();
		Date to = new Date(from.getTime()+8640000);
		service.getUkaProgram(null, from, to, false, null);
		Mockito.verify(mock).getUkaProgram(from, to);
	}
	
	@Test
	public void testWithAllIsTrueAsInput(){
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		service.getUkaProgram(null, null, null, true, null);
		Mockito.verify(mock).getUkaProgram();
	}
	
	
	@Test
	public void testWithOnlyPlaceAsInput(){
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		String place = "samfundet";
		service.getUkaProgram(null, null, null, false, place);
		Mockito.verify(mock).getUkaProgram(place);
	}
	@Test
	public void testWithDateAndPlaceAsInput(){
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		Date now = new Date();
		String place = "samfundet";
		service.getUkaProgram(now, null, null, false, place);
		Mockito.verify(mock).getUkaProgram(now, place);
	}
	@Test
	public void testWithDateFromAndTOAndPlaceAsInput(){
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		Date from = new Date();
		Date to = new Date(from.getTime()+8640000);
		String place = "samfundet";
		service.getUkaProgram(null, from, to, false, place);
		Mockito.verify(mock).getUkaProgram(from, to, place);
	}

	@Test
	public void testWithDateFromAndTOAndDateAsInput(){
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		Date from = new Date();
		Date to = new Date(from.getTime()+8640000);
		Date now = new Date();
		service.getUkaProgram(now, from, to, false, null);
		Mockito.verify(mock).getUkaProgram(now);
	}
	
	
}
