package no.uka.findmyapp.controller;

import java.util.Arrays;
import java.util.List;

import no.uka.findmyapp.model.Room;
import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;
import no.uka.findmyapp.service.PositionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/* Controller that handles HTTP requests for position
 * 
 * @author Cecilie Haugstvedt
 * @author Audun Sorheim
 * 
 */
@Controller
public class PositionController {

	@Autowired
	private PositionService service;

	private static final Logger logger = LoggerFactory
			.getLogger(PositionController.class);
	
	//maps the URL with SSID asking for position to page showing name associated with that SSID
	@RequestMapping(value = "/position/", method = RequestMethod.POST)
	public ModelAndView getPosition(@RequestBody Signal[] signals) {
		logger.info("getPosition ( " + signals.length + " )");
		ModelAndView mav = new ModelAndView("pos"); //pos.jsp is the name of the page displaying the result

		List<Signal> signalList = Arrays.asList(signals);
		Room room = service.getCurrentPosition(signalList);
		logger.info("getCurrentPosition ( " + room + " )");
		mav.addObject("room", room); // model name, model object 

		return mav;
	}
	
	@RequestMapping(value = "/position/sample/", method = RequestMethod.POST)
	public ModelAndView registerSample(@RequestBody Sample sample) {
		ModelAndView mav = new ModelAndView("regsample");
		boolean regSample = service.registerSample(sample);
		logger.info("registerSample ( " + regSample + " )");
		mav.addObject("regSample", regSample); // model name, model object
		
		return mav;
	}
	
	/*@ResponseBody
	@RequestMapping(value = "/position/sample")
	public List<Signal> getSample() {
		Signal signal = new Signal();
		signal.setBssid("Strossa");
		signal.setSignalStrength(5);
		Signal signal1 = new Signal();
		signal1.setBssid("Storsalen");
		signal1.setSignalStrength(7);
		Signal signal2 = new Signal();
		signal2.setBssid("Lyche");
		signal2.setSignalStrength(8);
		Sample sample = new Sample();
		sample.getSignalList().add(signal);
		sample.getSignalList().add(signal1);
		sample.getSignalList().add(signal2);
		return sample.getSignalList();
	}*/
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler(EmptyResultDataAccessException.class)
	private void handleEmptyResultDataAccessException(
			EmptyResultDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( "
				+ ex.getLocalizedMessage() + " )");
	}
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(IncorrectResultSizeDataAccessException.class)
	private void handleIncorrectResultSizeDataAccessException(
			IncorrectResultSizeDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( "
				+ ex.getLocalizedMessage() + " )");
	}
}
