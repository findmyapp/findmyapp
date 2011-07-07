package no.uka.findmyapp.service;

import java.text.ParseException;
import java.util.Date;

import no.uka.findmyapp.configuration.UkaProgramConfiguration;
import no.uka.findmyapp.datasource.UkaProgramRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("test-context.xml")
public class UkaProgramServiceTest {

	@Autowired
	UkaProgramConfiguration ukaProgramConfiguration;
	
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
