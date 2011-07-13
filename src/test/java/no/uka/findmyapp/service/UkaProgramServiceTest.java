package no.uka.findmyapp.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import no.uka.findmyapp.configuration.UkaProgramConfiguration;
import no.uka.findmyapp.configuration.UkaProgramConfigurationList;
import no.uka.findmyapp.datasource.UkaProgramRepository;
import no.uka.findmyapp.exception.UkaYearNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class UkaProgramServiceTest {

	UkaProgramConfigurationList list;
	Long millisInDay = (long) 86400000;
	
	@Before
	public void init() {
		List<UkaProgramConfiguration> tempList = new ArrayList<UkaProgramConfiguration>();
		UkaProgramConfiguration config = new UkaProgramConfiguration();
		config.setStartDate(new GregorianCalendar(1971, 1, 1).getTime());
		config.setEndDate(new GregorianCalendar(2020, 6, 1).getTime());
		config.setUkaYear("uka11");
		tempList.add(config);
		config = new UkaProgramConfiguration();
		config.setStartDate(new GregorianCalendar(2009, 1, 1).getTime());
		config.setEndDate(new GregorianCalendar(2009, 6, 1).getTime());
		config.setUkaYear("uka09");
		tempList.add(config);
		list = new UkaProgramConfigurationList();
		list.setList(tempList);
	}

	@Test
	public void testWithOnlyDateAsInput() throws ParseException, UkaYearNotFoundException {
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		service.setUkaProgramConfigurationList(list);
		Date now = new Date();
		service.getUkaProgram("uka11", now, null, null, null);
		Mockito.verify(mock).getUkaProgram(now, new Date(now.getTime()+millisInDay));
	}

	@Test
	public void testWithDateFromAndTOAsInput() throws UkaYearNotFoundException {
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		service.setUkaProgramConfigurationList(list);
		Date from = new Date();
		Date to = new Date(from.getTime() + millisInDay);
		service.getUkaProgram("uka11", null, from, to, null);
		Mockito.verify(mock).getUkaProgram(from, to);
	}

	@Test
	public void testWithAllIsTrueAsInput() throws UkaYearNotFoundException {
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		service.setUkaProgramConfigurationList(list);
		service.getUkaProgram("uka11", null, null, null, null);
		Mockito.verify(mock).getUkaProgram(list.get("uka11").getStartDate(), list.get("uka11").getEndDate());
	}

	@Test
	public void testWithOnlyPlaceAsInput() throws UkaYearNotFoundException {
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		service.setUkaProgramConfigurationList(list);
		String place = "samfundet";
		service.getUkaProgram("uka11", null, null, null, place);
		Mockito.verify(mock).getUkaProgram(list.get("uka11").getStartDate(), list.get("uka11").getEndDate(), place);
	}

	@Test
	public void testWithDateAndPlaceAsInput() throws UkaYearNotFoundException {
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		service.setUkaProgramConfigurationList(list);
		Date now = new Date();
		String place = "samfundet";
		service.getUkaProgram("uka11", now, null, null, place);
		Mockito.verify(mock).getUkaProgram(now, new Date(now.getTime() + millisInDay), place);
	}

	@Test
	public void testWithDateFromAndTOAndPlaceAsInput() throws UkaYearNotFoundException {
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		service.setUkaProgramConfigurationList(list);
		Date from = new Date();
		Date to = new Date(from.getTime() + millisInDay);
		String place = "samfundet";
		service.getUkaProgram("uka11", null, from, to, place);
		Mockito.verify(mock).getUkaProgram(from, to, place);
	}

	@Test
	public void testWithDateFromAndTOAndDateAsInput() throws UkaYearNotFoundException {
		UkaProgramService service = new UkaProgramService();
		UkaProgramRepository mock = Mockito.mock(UkaProgramRepository.class);
		service.setUkaProgramRepository(mock);
		service.setUkaProgramConfigurationList(list);
		Date from = new Date();
		Date to = new Date(from.getTime() + 10);
		Date now = new Date();
		service.getUkaProgram("uka11", now, from, to, null);
		Mockito.verify(mock).getUkaProgram(now, new Date(now.getTime() + millisInDay));
	}

}
