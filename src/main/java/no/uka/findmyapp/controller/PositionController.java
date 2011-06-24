package no.uka.findmyapp.controller;

import no.uka.findmyapp.datasource.PositionDataHandler;
import no.uka.findmyapp.model.Position;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/* Controller that handles HTTP requests for position
 * 
 * @author Cecilie Haugstvedt
 * @author Audun Sørheim
 * 
 */
@Controller
public class PositionController {

	@Autowired
	private PositionDataHandler data;

	private static final Logger logger = LoggerFactory
			.getLogger(PositionController.class);
	
	//maps the URL with SSID asking for position to page showing name associated with that SSID
	@RequestMapping(value = "/position/{ssid}", method = RequestMethod.GET)
	public ModelAndView getPosition(
			@PathVariable String ssid) {
		logger.info("getPosition ( " + ssid + " )");

		ModelAndView mav = new ModelAndView("pos"); //pos.jsp is the name of the page displaying the result
		Position pos = data.getPosition(ssid);
		mav.addObject("position", pos); // model name, model object 

		return mav;
	}
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler(EmptyResultDataAccessException.class) //must be parameterized when there is more than one error handler like this
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
