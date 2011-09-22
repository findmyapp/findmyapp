package no.uka.findmyapp.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import no.uka.findmyapp.datasource.SpotifyRepository;
import no.uka.findmyapp.exception.LocationNotFoundException;
import no.uka.findmyapp.exception.MusicSessionNotOpenException;
import no.uka.findmyapp.exception.SpotifyApiException;
import no.uka.findmyapp.model.spotify.SpotifyTrack;
import no.uka.findmyapp.model.spotify.SpotifyTrackSearchContainer;
import no.uka.findmyapp.model.spotify.Track;
import no.uka.findmyapp.model.spotify.MusicSession;
import no.uka.findmyapp.model.spotify.SpotifyLookupContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@Service
public class SpotifyService {
	private static final Logger logger = LoggerFactory
			.getLogger(FacebookService.class);
	@Autowired
	private SpotifyRepository data;
	
	@Autowired
	private Gson gson;
	
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
	
	public boolean requestSong(String spotifyId, int userId, int locationId, String code) throws SpotifyApiException, MusicSessionNotOpenException {
		boolean success = false;
		//check if code exists
		if (!data.hasSong(spotifyId)) {
			SpotifyLookupContainer song = requestSpotifySong(spotifyId);
			logger.debug(song.getTrack().getName()+ " - Fetched from spotify, length: "+song.getTrack().getLength());
			long length = (long) song.getTrack().getLength()*1000;
			data.saveSong(spotifyId, song.getTrack().getName(), song.getTrack().concatArtistNames(), length);
		}
		
		if (!data.getSession(locationId).isOpen()) {
			throw new MusicSessionNotOpenException("Session is not open");
		} else {
			success = data.requestSong(spotifyId, locationId, userId);
			if (success) {
				//set code as used
			}
		}
		
		return success;
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
	
}
