package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.Accesspoint;

import org.springframework.jdbc.core.RowMapper;

public class AccesspointRowMapper implements RowMapper<Accesspoint> {

	public Accesspoint mapRow(ResultSet rs, int rowNum) throws SQLException {
		Accesspoint ap = new Accesspoint();
		ap.setBssid(rs.getString("bssid"));
		return ap;
	}
}
