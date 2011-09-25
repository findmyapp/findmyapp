package no.uka.findmyapp.datasource;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.MusicSessionRowMapper;
import no.uka.findmyapp.datasource.mapper.TrackRowMapper;
import no.uka.findmyapp.model.spotify.MusicSession;
import no.uka.findmyapp.model.spotify.Track;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SpotifyRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	private static final Logger logger = LoggerFactory
			.getLogger(SensorRepository.class);
	
	
	private static final String songJoinString = "SELECT ti.banned, ti.spotify_id, ti.title, ti.artist, ti.length, th.last_played, "+
			"th.times_played, SUM( num_of_requests )  AS total_requests, SUM( IF (active = 'true', 1, 0) ) AS active_requests "+
			"FROM (SELECT * FROM POSITION_LOCATION WHERE position_location_id = :lId) AS l JOIN TRACK_INFO AS ti " +
			"LEFT JOIN TRACK_REQUEST AS tr ON (tr.spotify_id=ti.spotify_id AND tr.position_location_id=l.position_location_id) " +
			"LEFT JOIN TRACK_PLAYHISTORY AS th ON (th.spotify_id=ti.spotify_id AND th.position_location_id=l.position_location_id) ";
	
	
	//"WHERE l.position_location_id = :lId GROUP BY ti.spotify_id %s %s";
	
	public Track getSong(String spotifyId, int locationId) {
		Map<String, Object> namedParams = getMap(locationId, spotifyId, -1, null, -1, -1, null);
		NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
		return jdbc.queryForObject(songJoinString + "WHERE ti.spotifyId = :sId", 
				namedParams, new TrackRowMapper());
	}
	
	public List<Track> getSongs(List<String> spotifyIds, int locationId, String orderBy) {
		Map<String, Object> namedParams = getMap(locationId, null, -1, spotifyIds, -1, -1, null);
		NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
		return jdbc.query(songJoinString + "WHERE ti.spotify_id IN (:sIds) GROUP BY ti.spotify_id "+
				getOrderString(orderBy), namedParams, new TrackRowMapper());
	}
	
	public List<Track> getSongs(int locationId, int from, int to, String orderBy, Timestamp notPlayedSince) {
		Map<String, Object> namedParams = getMap(locationId, null, -1, null, from, to, notPlayedSince);
		NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
		return jdbc.query(songJoinString + "WHERE (th.last_played IS NULL OR th.last_played < :notSince)" +
				" GROUP BY ti.spotify_id "+getOrderString(orderBy)+" LIMIT :from, :to", namedParams, new TrackRowMapper());
	}
	
	public List<Track> getPlayedTracks(int locationId, int from, int to) {
		Map<String, Object> namedParams = getMap(locationId, null, -1, null, from, to, null);
		NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
		return jdbc.query(songJoinString + "ORDER BY last_played DESC LIMIT :from, :to", namedParams, new TrackRowMapper());
	}
	
	public boolean hasSong(String spotifyId) {
		int n = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM TRACK_INFO WHERE spotify_id = ?", spotifyId);
		return (n > 0);
	}
	
	public void saveSong(String spotifyId, String title, String artist, long length) {
		jdbcTemplate.update("INSERT INTO TRACK_INFO VALUES ( ?, ?, ?, ?, ? ) " +
				"ON DUPLICATE KEY UPDATE length = length", spotifyId, title, artist, length, "false");
	}
	
	public void setSongAsPlayed(String spotifyId, int locationId) {
		jdbcTemplate.update("INSERT INTO TRACK_PLAYHISTORY VALUES ( ?, ?, NOW(), 1) " +
				"ON DUPLICATE KEY UPDATE times_played = times_played + 1, last_played = NOW()", spotifyId, locationId);
		jdbcTemplate.update("UPDATE TRACK_REQUEST SET active = 'false'");
	}
	
	public boolean requestSong(String spotifyId, int locationId, int userId) {
		int num = jdbcTemplate.update("INSERT INTO TRACK_REQUEST VALUES ( ?, ?, ?, 'true', 1 ) ON DUPLICATE KEY " +
				"UPDATE num_of_requests = IF ( active = 'false', num_of_requests + 1, num_of_requests ), active = 'true'", spotifyId, locationId, userId);
		return num > 0;
	}
	
	public boolean removeRequests(int locationId) {
		int num = jdbcTemplate.update("UPDATE TRACK_REQUEST SET active = 'false' WHERE position_location_id = ?", locationId);
		return (num > 0);
	}
	
	public boolean createOrUpdateSession(int locationId, String sessionName, boolean open) {
		int num = jdbcTemplate.update("INSERT INTO TRACK_SESSIONS VALUES ( ?, ?, ? ) ON DUPLICATE KEY "+
				"UPDATE session_name = ?, open = ?", locationId, sessionName, open, sessionName, open);
		return num > 0;
	}
	public boolean updateSession(int locationId, boolean open) {
		int num = jdbcTemplate.update("UPDATE TRACK_SESSIONS SET open = ? WHERE position_location_id = ?", open, locationId);
		return num > 0;
	}
	
	public List<MusicSession> getSessions() {
		return jdbcTemplate.query("SELECT * FROM TRACK_SESSIONS NATURAL JOIN POSITION_LOCATION", new MusicSessionRowMapper());
	}
	
	public MusicSession getSession(int locationId) {
		return jdbcTemplate.queryForObject("SELECT * FROM TRACK_SESSIONS NATURAL JOIN POSITION_LOCATION WHERE position_location_id = ?", new MusicSessionRowMapper(), locationId);
	}
	
	/**
	 * Method to add all parameters used to Map
	 */
	private Map<String, Object> getMap(int locationId, String spotifyId, int userId, List<String> spotifyIds, int from, int to, Timestamp notPlayedSince) {
		Map<String, Object> namedParams = new HashMap<String, Object>();
		namedParams.put("lId", locationId);
		namedParams.put("uId", userId);
		namedParams.put("sId", spotifyId);
		namedParams.put("sIds", spotifyIds);
		namedParams.put("from", from);
		namedParams.put("to", to);
		namedParams.put("notSince", notPlayedSince);
		return namedParams;
	}
	/**
	 * Method to get useful ordering of songs
	 */
	private String getOrderString(String orderBy) {
		if ("totalrequests".equalsIgnoreCase(orderBy)) {
			return "ORDER BY total_requests DESC, times_played DESC, last_played ASC";
		}
		else if ("timesplayed".equalsIgnoreCase(orderBy)) {
			return "ORDER BY times_played DESC, total_requests DESC, last_played ASC";
		}
		else {//active requests
			return "ORDER BY active_requests DESC, times_played DESC, last_played ASC";
		}
	}	
}
