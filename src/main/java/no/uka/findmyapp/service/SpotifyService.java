package no.uka.findmyapp.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import no.uka.findmyapp.datasource.SpotifyRepository;
import no.uka.findmyapp.exception.MusicSessionNotOpenException;
import no.uka.findmyapp.exception.QRCodeNotValidException;
import no.uka.findmyapp.exception.SpotifyApiException;
import no.uka.findmyapp.exception.UpdateQRCodeException;
import no.uka.findmyapp.model.spotify.RequestResponse;
import no.uka.findmyapp.model.spotify.SpotifyTrack;
import no.uka.findmyapp.model.spotify.SpotifyTrackSearchContainer;
import no.uka.findmyapp.model.spotify.Track;
import no.uka.findmyapp.model.spotify.MusicSession;
import no.uka.findmyapp.model.spotify.SpotifyLookupContainer;
import no.uka.findmyapp.service.auth.AuthenticationService;
import no.uka.findmyapp.service.auth.QRService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import static no.uka.findmyapp.constants.QRConstants.*;

@Service
public class SpotifyService {
	private static final Logger logger = LoggerFactory
			.getLogger(FacebookService.class);
	@Autowired
	private SpotifyRepository data;

	@Autowired
	private QRService qrService;

	@Autowired
	private Gson gson;
	
	@Autowired
	private AuthenticationService authService;
	

	public List<MusicSession> getSessions() {
		return data.getSessions();
	}

	public MusicSession getSession(int locationId) {
		return data.getSession(locationId);
	}

	public Track getSong(String spotifyId, int locationId) {
		return data.getSong(spotifyId, locationId);
	}

	public List<Track> getSongs(int from, int num, String orderBy, long notPlayedIn, int locationId) {
		int to = from + Math.abs(num);
		Timestamp notPlayedSince = new Timestamp(System.currentTimeMillis()-notPlayedIn);
		return data.getSongs(locationId, from, to, orderBy, notPlayedSince);
	}

	public List<Track> getPlayedSongs(int from, int num, int locationId) {
		int to = from + Math.abs(num);
		return data.getPlayedTracks(locationId, from, to);
	}

	public RequestResponse requestSong(String spotifyId, String token, int locationId, String code) throws SpotifyApiException, UpdateQRCodeException {
		boolean success = false;
		
		if (!data.hasSong(spotifyId)) {//Fetch song info from spotify if neccessary
			SpotifyLookupContainer song = requestSpotifySong(spotifyId);
			logger.debug(song.getTrack().getName()+ " - Fetched from spotify, length: "+song.getTrack().getLength());
			long length = (long) song.getTrack().getLength()*1000;
			data.saveSong(spotifyId, song.getTrack().getName(), song.getTrack().concatArtistNames(), length);
		}
			
		
		int qrValidationCode = qrService.verify(code, locationId);
		if (!data.getSession(locationId).isOpen()) {//session is not open
			return new RequestResponse("Stemming er ikke mulig. Sesjonen er ikke aktiv", false, qrValidationCode);
		}
		
		else if (qrValidationCode==QR_IS_NOT_VALID){//Code is not valid
			return new RequestResponse("QR-koden er ikke gyldig", false, qrValidationCode);
		} 
		else if (token == null && qrValidationCode == QR_HAS_UNLIMITED_USES) {//Unlimited votes so requires token
			throw new InvalidTokenException("This QRcode requires login");//Det her burde kanskje bare returnere at man trenger token og ikke kaste exception?
		} 
		else if (qrValidationCode == QR_HAS_UNLIMITED_USES) {//Unlimited votes so check token
			int userId = verifyToken(token);//throws exception if code is not valid
			
			if (data.userCanRequestSong(spotifyId, locationId, userId)){
				success = data.requestSongOneActiveVote(spotifyId, locationId, userId);
				if (success) {
					if (!qrService.codeIsUsed(code)){
						//throw new UpdateQRCodeException("Could not update QRCode status");//Er denne noedvendig? Holder det ikke aa logge? Er ikke brukerens problem om vi ikke klarer aa oppdatere en qrkode vi sjekket var gyldig
						logger.debug("Could not update QRcode");
					}
				}else {
					logger.debug("User "+ userId+ " could not vote for song "+spotifyId+ " at location "+ locationId);
					return new RequestResponse("Denne brukeren har allerede stemt opp denne sangen", false, qrValidationCode);
				}
			}
		} 
		else if (qrValidationCode == QR_HAS_LIMITED_USES) {//Limited votes, so token not neccessary
			int userId = authService.verify(token);
			if (userId == -1) {//Token null or not valid
				userId = -1337;//The fake spotify user
			}
			success = data.requestSongManyActiveVotes(spotifyId, locationId, userId);//No need to update qrcodes and such.//Jo? Have to decrement the uses.
			if (success){
				if (!qrService.codeIsUsed(code)){
					logger.debug("Could not update QRcode");
				}
			} else {
				return new RequestResponse("Kunne ikke oppdatere databasen", false, qrValidationCode);
			}
		}

		return new RequestResponse("Stemmen ble registrert", success, qrValidationCode);
	}

	public List<Track> searchForTrack(String query, String orderBy, int page, int locationId) throws SpotifyApiException {
		SpotifyTrackSearchContainer tracks = searchSpotify(query, page);
		List<String> spotifyIds = new ArrayList<String>();

		for (SpotifyTrack track: tracks.getTracks()) {//Save tracks to db
			long length = (long) track.getLength() * 1000;
			data.saveSong(track.getHref(), track.getName(), track.concatArtistNames(), length);
			logger.debug("Found - "+track.getName()+" id: "+track.getHref());
			spotifyIds.add(track.getHref());
		}
		if (spotifyIds.size() > 0) {
			return data.getSongs(spotifyIds, locationId, orderBy);
		} else {//to prevent sql-error when there are no ids.
			return new ArrayList<Track>();
		}
	}

	public Track setSongAsPlaying(String spotifyId, int locationId) {
		data.setSongAsPlayed(spotifyId, locationId);
		return data.getSong(spotifyId, locationId);
	}

	public boolean removeRequests(int locationId) {
		return data.removeRequests(locationId);
	}

	public boolean createOrUpdateSession(int locationId, boolean open, String sessionName, boolean resetVotes) {
		if (resetVotes) {
			removeRequests(locationId);
		}
		if (sessionName == null) {
			return data.updateSession(locationId, open);
		} else {
			return data.createOrUpdateSession(locationId, sessionName, open);
		}
	}


	/**
	 * Adapter for spotify's search api
	 * @param query - to match tracks
	 * @param page - what page with results to see
	 * @return Tracks matching query
	 * @throws SpotifyApiException - Error contacting spotify
	 */
	private SpotifyTrackSearchContainer searchSpotify(String query, int page) throws SpotifyApiException {
		RestTemplate rest = new RestTemplate();
		SpotifyTrackSearchContainer response = null;

		try {
			logger.debug("Searching for spotify-songs with query "+query);
			String jsonResponse = rest.getForObject("http://ws.spotify.com/search/1/track.json?q="+query+"&page="+page, String.class);
			response = gson.fromJson(jsonResponse, SpotifyTrackSearchContainer.class);
		} catch (RestClientException e) {
			logger.error("Exception while searching for spotifysongs matching "+query+" error: "+e.getMessage());
			throw new SpotifyApiException(e.getMessage());
		} catch (JsonSyntaxException e) {
			logger.error("Error reading response from spotify: "+response+" error: "+e.getMessage());
			throw new SpotifyApiException(e.getMessage());
		}
		return response;
	}

	/**
	 * Adapter for spotify's lookup api
	 * @param spotifyId - id of track, has to start with "spotify:track:"
	 * @return Track with given id
	 * @throws SpotifyApiException - Error contacting spotify
	 * @throws IllegalArgumentException - Is thrown when trying to fetch data that isn't a track
	 */
	private SpotifyLookupContainer requestSpotifySong(String spotifyId) throws SpotifyApiException {
		RestTemplate rest = new RestTemplate();
		SpotifyLookupContainer response = null;

		if (spotifyId.indexOf("spotify:track:") != 0) {//trying to look up something that isn't a track
			throw new IllegalArgumentException();
		}

		try {
			logger.debug("Fetching spotifysong with uri: "+spotifyId);
			String jsonResponse = rest.getForObject("http://ws.spotify.com/lookup/1/.json?uri="+spotifyId, String.class);
			response = gson.fromJson(jsonResponse, SpotifyLookupContainer.class);//use gson rather than built in spring deserializer which needs the object to match all fields

		} catch (RestClientException e) {
			logger.error("Exception while fetching spotifySong "+spotifyId+" error: "+e.getMessage());
			throw new SpotifyApiException(e.getMessage());
		}
		return response;
	}
	
	private int verifyToken(String token) throws InvalidTokenException {
		int tokenUserId = authService.verify(token);
		if (tokenUserId == -1)
			throw new InvalidTokenException("Invalid access token");
		return tokenUserId;
	}

}
