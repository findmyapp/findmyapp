package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.CustomParameterDetailed;

import org.springframework.jdbc.core.RowMapper;

public class CustomParameterDetailedRowMapper implements RowMapper<CustomParameterDetailed> {
	public CustomParameterDetailed mapRow(ResultSet rs, int rowNum) throws SQLException {
		CustomParameterDetailed customParameter = new CustomParameterDetailed();
		customParameter.setCustomParameterId(rs.getInt("custom_parameter_id"));
		customParameter.setParameterName(rs.getString("parameter_name"));
		customParameter.setEntryCount(rs.getInt("count"));
		return customParameter;
	}
}
