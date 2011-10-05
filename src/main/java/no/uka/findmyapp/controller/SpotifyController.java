package no.uka.findmyapp.controller;

import no.uka.findmyapp.exception.MusicSessionNotOpenException;
import no.uka.findmyapp.exception.QRCodeNotValidException;
import no.uka.findmyapp.exception.SpotifyApiException;
import no.uka.findmyapp.exception.UpdateQRCodeException;
import no.uka.findmyapp.helpers.ServiceModelMapping;
import no.uka.findmyapp.service.SpotifyService;
import no.uka.findmyapp.service.auth.AuthenticationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/spotify")
public class SpotifyController {
	@Autowired
	private AuthenticationService authService;
	
	@Autowired
	private SpotifyService service;
	
	private static final Logger logger = LoggerFactory
			.getLogger(LocationController.class);
	
	/**
	 * Method just to check if you entered a valid password
	 * @return
	 */
	@Secured("ROLE_DJ")
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public ModelAndView isLoggedIn() {
		return new ModelAndView("json", "response", "pong");
	}
	
	/**
	 * Get all musicsessions
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getSessions() {
		ModelAndView model = new ModelAndView("json");
		model.addObject("sessions", service.getSessions());
		return model;
	}
	
	/**
	 * Get musicsession with given id
	 * @param locationId
	 * @return
	 */
	@RequestMapping(value = "/{locationId}", method = RequestMethod.GET)
	public ModelAndView getSession(@PathVariable int locationId) {
		ModelAndView model = new ModelAndView("json");
		model.addObject("session", service.getSession(locationId));
		return model;
	}
	
	/**
	 * Get our information about the specified song
	 * @param locationId
	 * @param spotifyId
	 * @return
	 */
	@RequestMapping(value = "/{locationId}/songs/{spotifyId}", method = RequestMethod.GET)
	public ModelAndView getSong(@PathVariable int locationId, @PathVariable String spotifyId) {
		ModelAndView model = new ModelAndView("json");
		model.addObject("song", service.getSong(spotifyId, locationId));
		return model;
	}
	
	/**
	 * See the most popular songs (given from ordering)
	 * @param locationId
	 * @param fromIndex - show from the nth most popular songs
	 * @param num - don't show more than n songs
	 * @param notPlayedIn - don't show songs played in the last n milliseconds
	 * @param orderBy - ordering of list, allowed values are activeRequests, timesPlayed and totalRequests
	 * @return
	 */
	@RequestMapping(value = "/{locationId}/songs", method = RequestMethod.GET)
	public ModelAndView getSongs(@PathVariable int locationId, 
			@RequestParam(required = false, defaultValue = "0") int fromIndex,
			@RequestParam(required = false, defaultValue = "10") int num,
			@RequestParam(required = false, defaultValue = "0") long notPlayedIn,
			@RequestParam(required = false, defaultValue = "activeRequests") String orderBy) {
		
		ModelAndView model = new ModelAndView("json");
		model.addObject("songs", service.getSongs(fromIndex, num, orderBy, notPlayedIn, locationId));
		return model;
	}
	
	
	@RequestMapping(value = "/{locationId}/songs/played", method = RequestMethod.GET)
	public ModelAndView getPastSongs(@PathVariable int locationId,
			@RequestParam(required = false, defaultValue = "0") int fromIndex,
			@RequestParam(required = false, defaultValue = "10") int num) {
		
		ModelAndView model = new ModelAndView("json");
		model.addObject("songs", service.getPlayedSongs(fromIndex, num, locationId));
		return model;
	}
	
	/**
	 * Use spotify's metadata api to search for songs and order them by popularity
	 * @param locationId
	 * @param query - query to search for
	 * @param orderBy - how the songs should be ordered. Valid values are activeRequests, timesPlayed and totalRequests
	 * @param page - For seeing worse matched results (see spotify search api)
	 * @return
	 * @throws SpotifyApiException - Error while contacting spotify
	 */
	@RequestMapping(value = "{locationId}/songs/search", method = RequestMethod.GET)
	public ModelAndView searchForTrack(@PathVariable int locationId,
			@RequestParam String query,
			@RequestParam(required = false, defaultValue = "activeRequests") String orderBy,
			@RequestParam(required = false, defaultValue = "1") int page) throws SpotifyApiException {
		ModelAndView model = new ModelAndView("json");
		model.addObject("tracks", service.searchForTrack(query, orderBy, page, locationId));
		return model;
	}
	
	/**
	 * For requesting song
	 * @param locationId
	 * @param spotifyId
	 * @param code - A unused qr-code
	 * @param token
	 * @return
	 * @throws SpotifyApiException - Error while contacting spotify
	 * @throws MusicSessionNotOpenException - Thrown when the music session isn't open
	 * @throws QRCodeNotValidException 
	 * @throws UpdateQRCodeException 
	 */
	@Secured("ROLE_CONSUMER")
	@RequestMapping(value = "/{locationId}/songs/{spotifyId}", method = RequestMethod.POST)
	public ModelAndView postRequest(
			@PathVariable int locationId, 
			@PathVariable String spotifyId,
			@RequestParam(required=true) String code,
			@RequestParam(required=true) String token) throws SpotifyApiException, UpdateQRCodeException {
		logger.debug("user token: " + token);
		int tokenUserId = verifyToken(token);
		ModelAndView model = new ModelAndView("json");
		model.addObject("song", service.requestSong(spotifyId, tokenUserId, locationId, code));
		return model;
	}
	
	/**
	 * Set song as playing. Updates the number of times the song has been played and sets all requests for this song as unactive
	 * @param locationId
	 * @param spotifyId
	 * @return
	 */
	@Secured("ROLE_DJ")
	@RequestMapping(value = "/{locationId}/songs/{spotifyId}/isPlaying", method = RequestMethod.POST)
	public ModelAndView isPlayingSong(@PathVariable int locationId, @PathVariable String spotifyId) {
		ModelAndView model = new ModelAndView("json");
		model.addObject("song", service.setSongAsPlaying(spotifyId, locationId));
		return model;
	}
	
	/**
	 * Sets all requests at this location as unactive
	 * @param locationId
	 * @return
	 */
	@Secured("ROLE_DJ")
	@RequestMapping(value = "/{locationId}/requests", method = RequestMethod.DELETE)
	public ModelAndView deleteRequests(@PathVariable int locationId) {
		ModelAndView model = new ModelAndView("json");
		model.addObject("success", service.removeRequests(locationId));
		return model;
	}
	
	/**
	 * Creates or updates a given session
	 * @param locationId
	 * @param open - set sessions as open or closed
	 * @param sessionName - name of the session
	 * @param resetVotes - if value is true then all votes will be reset
	 * @return
	 */
	@Secured("ROLE_DJ")
	@RequestMapping(value = "/{locationId}", method = RequestMethod.POST)
	public ModelAndView createOrUpdateSession(@PathVariable int locationId,
			@RequestParam(required=true) boolean open,
			@RequestParam(required=false) String sessionName,
			@RequestParam(required=false, defaultValue="false") boolean resetVotes) {
		ModelAndView model = new ModelAndView("json");
		model.addObject("success", service.createOrUpdateSession(locationId, open, sessionName, resetVotes));
		return model;
	}
	
	
	private int verifyToken(String token) throws InvalidTokenException {
		int tokenUserId = authService.verify(token);
		if (tokenUserId == -1)
			throw new InvalidTokenException("Invalid access token");
		return tokenUserId;
	}
	
	@SuppressWarnings("unused")
	@ExceptionHandler(InvalidTokenException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Provided token is not valid")
	private void handleInvalidTokenException(InvalidTokenException e) {
		logger.debug(e.getMessage());
	}
	
	@SuppressWarnings("unused")
	@ExceptionHandler(SpotifyApiException.class)
	@ResponseStatus(value = HttpStatus.BAD_GATEWAY, reason = "Error while contacting spotify")
	private void handleInvalidSpotifyApiException(SpotifyApiException e) {
		logger.debug(e.getMessage());
	}
	
	@SuppressWarnings("unused")
	@ExceptionHandler(MusicSessionNotOpenException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "The music session is not currently open")
	private void handleMusicSessionNotOpenException(MusicSessionNotOpenException e) {
		logger.debug(e.getMessage());
	}
	
	@SuppressWarnings("unused")
	@ExceptionHandler(QRCodeNotValidException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "The provided qr-code is not valid")
	private void handleQRCodeNotValidException(QRCodeNotValidException e) {
		logger.debug(e.getMessage());
	}
}
