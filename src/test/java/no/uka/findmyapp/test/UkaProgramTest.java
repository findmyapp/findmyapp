package no.uka.findmyapp.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import no.uka.findmyapp.controller.UkaProgramController;
import no.uka.findmyapp.model.UkaProgram;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/WEB-INF/spring/root-context.xml",
		"/WEB-INF/spring/appServlet/controllers.xml" })
public class UkaProgramTest {

	@Autowired
	UkaProgramController controller;

	@Test
	public void checkNumberFormattingOnInput() {

		String dateString = "2010-01-01";
		String datePattern = "yyyy-MM-dd";

		ModelAndView modelAndView = null;
		try {
			modelAndView = controller
					.getUkaProgramForDate(new SimpleDateFormat(datePattern)
							.parse(dateString));
		} catch (ParseException e) {
			fail(e.getLocalizedMessage());
		}

		if (modelAndView != null) {
			UkaProgram program = (UkaProgram) modelAndView.getModelMap().get(
					"program");
			assertTrue(new SimpleDateFormat(datePattern).format(
					program.getDay()).equals(dateString));
		} else {
			fail();
		}
	}
}