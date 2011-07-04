package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;

import org.springframework.jdbc.core.RowMapper;

public class SampleRowMapper implements RowMapper<Sample> {

	public Sample mapRow(ResultSet rs, int rowNum) throws SQLException {
		Sample sample = new Sample();
		sample.setId(rs.getInt("position_sample_id"));
		sample.setRoomId(rs.getInt("position_room_id"));
		sample.setSignalList(new ArrayList<Signal>());
		Signal signal = new Signal();
		signal.setBssid(rs.getString("signal.position_accesspoint_bssid"));
		signal.setSignalStrength(rs.getInt("signal.signal_strength"));
		sample.getSignalList().add(signal);
		return sample;
	}
}
