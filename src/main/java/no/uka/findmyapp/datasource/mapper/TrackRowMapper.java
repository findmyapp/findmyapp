package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import no.uka.findmyapp.model.spotify.Track;

import org.springframework.jdbc.core.RowMapper;

public class TrackRowMapper implements RowMapper<Track> {

	public Track mapRow(ResultSet rs, int rowNum) throws SQLException {
		Track track = new Track();
		track.setSpotifyId(rs.getString("spotify_id"));
		track.setTitle(rs.getString("title"));
		track.setArtist(rs.getString("artist"));
		track.setLength(rs.getLong("length"));
		track.setLastPlayed(rs.getTimestamp("last_played"));
		track.setTimesPlayed(rs.getInt("times_played"));
		track.setActiveRequests(rs.getInt("active_requests"));
		track.setTotalRequests(rs.getInt("total_requests"));
		track.setBanned(rs.getBoolean("banned"));
		return track;
	}
	
}