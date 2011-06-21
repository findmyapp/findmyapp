package no.uka.findmyapp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class UkaProgramTest {

	@Test
	public void checkNumberFormattingOnInput() {
		RestTemplate restTemplate = new RestTemplate();
		String param = "2011-10-07";
		Date date = restTemplate.getForObject("http://localhost:8080/findmyapp/program/{day}", Date.class, param);
		assertEquals(date.toString(), param);
	}
}
