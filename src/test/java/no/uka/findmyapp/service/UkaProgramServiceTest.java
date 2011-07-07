package no.uka.findmyapp.service;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

import no.uka.findmyapp.configuration.UkaProgramConfiguration;
import no.uka.findmyapp.datasource.UkaProgramRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class UkaProgramServiceTest {

	UkaProgramConfiguration ukaProgramConfiguration;
	
	@Before
	public void init() {
		ukaProgramConfiguration = new UkaProgramConfiguration();
		ukaProgramConfiguration.setStartDate(new GregorianCalendar(2011, 1, 1).getTime());
		ukaProgramConfiguration.setEndDate(new GregorianCalendar(2011, 6, 1).getTime());
		ukaProgramConfiguration.setUkaYearForStartAndEndDate("uka11");
	}

	@Test
	public void testWithOnlyDateAsInput() throws ParseException {
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		service.setUkaProgramConfiguration(ukaProgramConfiguration);
		Date now = new Date();
		service.getUkaProgram("uka11", now, null, null, null, null);
		Mockito.verify(mock).getUkaProgram(now);
	}

	@Test
	public void testWithDateFromAndTOAsInput() {
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		service.setUkaProgramConfiguration(ukaProgramConfiguration);
		Date from = new Date();
		Date to = new Date(from.getTime() + 8640000);
		service.getUkaProgram("uka11", null, from, to, false, null);
		Mockito.verify(mock).getUkaProgram(from, to);
	}

	@Test
	public void testWithAllIsTrueAsInput() {
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		service.setUkaProgramConfiguration(ukaProgramConfiguration);
		service.getUkaProgram("uka11", null, null, null, true, null);
		Mockito.verify(mock).getUkaProgram();
	}

	@Test
	public void testWithOnlyPlaceAsInput() {
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		service.setUkaProgramConfiguration(ukaProgramConfiguration);
		String place = "samfundet";
		service.getUkaProgram("uka11", null, null, null, false, place);
		Mockito.verify(mock).getUkaProgram(place);
	}

	@Test
	public void testWithDateAndPlaceAsInput() {
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		service.setUkaProgramConfiguration(ukaProgramConfiguration);
		Date now = new Date();
		String place = "samfundet";
		service.getUkaProgram("uka11", now, null, null, false, place);
		Mockito.verify(mock).getUkaProgram(now, place);
	}

	@Test
	public void testWithDateFromAndTOAndPlaceAsInput() {
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		service.setUkaProgramConfiguration(ukaProgramConfiguration);
		Date from = new Date();
		Date to = new Date(from.getTime() + 8640000);
		String place = "samfundet";
		service.getUkaProgram("uka11", null, from, to, false, place);
		Mockito.verify(mock).getUkaProgram(from, to, place);
	}

	@Test
	public void testWithDateFromAndTOAndDateAsInput() {
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		service.setUkaProgramConfiguration(ukaProgramConfiguration);
		Date from = new Date();
		Date to = new Date(from.getTime() + 8640000);
		Date now = new Date();
		service.getUkaProgram("uka11", now, from, to, false, null);
		Mockito.verify(mock).getUkaProgram(now);
	}

}
