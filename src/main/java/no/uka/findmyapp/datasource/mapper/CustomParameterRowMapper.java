package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import no.uka.findmyapp.model.CustomParameter;

public class CustomParameterRowMapper implements RowMapper<CustomParameter> {
	public CustomParameter mapRow(ResultSet rs, int rowNum) throws SQLException {
		CustomParameter customParameter = new CustomParameter();
		customParameter.setCustomParameterId(rs.getInt("custom_parameter_id"));
		customParameter.setParameterName(rs.getString("parameter_name"));
		return customParameter;
	}
}
