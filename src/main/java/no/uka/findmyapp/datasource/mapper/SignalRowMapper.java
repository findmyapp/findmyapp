package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.Signal;

import org.springframework.jdbc.core.RowMapper;

public class SignalRowMapper implements RowMapper<Signal> {

	public Signal mapRow(ResultSet rs, int rowNum) throws SQLException {
		Signal signal = new Signal();
		signal.setBssid(rs.getString("position_accesspoint_bssid"));
		signal.setSignalStrength(rs.getInt("signal_strength"));
		return signal;
	}
}
