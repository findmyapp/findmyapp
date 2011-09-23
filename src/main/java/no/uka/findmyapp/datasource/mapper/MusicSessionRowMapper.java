package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.spotify.MusicSession;

import org.springframework.jdbc.core.RowMapper;

public class MusicSessionRowMapper implements RowMapper<MusicSession> {

	@Override
	public MusicSession mapRow(ResultSet rs, int rowNum) throws SQLException {
		MusicSession session = new MusicSession();
		session.setLocationId(rs.getInt("position_location_id"));
		session.setStringId(rs.getString("string_id"));
		session.setLocationName(rs.getString("name"));
		
		session.setSessionName(rs.getString("session_name"));
		session.setOpen(rs.getBoolean("open"));
		return session;
	}
	
}
