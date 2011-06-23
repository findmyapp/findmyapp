package no.uka.findmyapp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import no.uka.findmyapp.controller.UkaProgramController;
import no.uka.findmyapp.model.UkaProgram;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

public class UkaProgramTest {

	@Autowired
	UkaProgramController controller;
	
	@Test
	public void checkNumberFormattingOnInput() {
		RestTemplate restTemplate = new RestTemplate();
		String param = "2010-01-01";
		ModelAndView modelAndView = null;
		try {
			modelAndView = controller.getUkaProgramForDate(new SimpleDateFormat("yyyy-MM-dd").parse("2011-01-01"));
		} catch (ParseException e) {
			fail(e.getLocalizedMessage());
		}
		if (modelAndView != null) {
			
		} else {
			fail();
		}
		UkaProgramWrapper programWrapper = restTemplate.getForObject("http://localhost:8080/findmyapp/program/{day}", UkaProgramWrapper.class, param);
		UkaProgram program = programWrapper.getProgram();
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(program.getDay()), param);
	}
	
	@JsonAutoDetect
	private class UkaProgramWrapper {
		
		@JsonProperty("program")
		private UkaProgram program;

		public UkaProgram getProgram() {
			return program;
		}

		public void setProgram(UkaProgram program) {
			this.program = program;
		}
	}
}