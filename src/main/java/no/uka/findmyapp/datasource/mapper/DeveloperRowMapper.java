package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.appstore.Developer;

import org.springframework.jdbc.core.RowMapper;

public class DeveloperRowMapper  implements RowMapper<Developer>{
	public Developer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Developer developer = new Developer();
		developer.setDeveloperID(rs.getString("appstore_developer_id"));
		developer.setEmail(rs.getString("email"));
		developer.setFullName(rs.getString("fullname"));
		developer.setUserId(rs.getInt("user_id"));
		developer.setWpId(rs.getInt("wp_id"));
		return developer;
	}
}
