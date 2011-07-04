package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;

import org.springframework.jdbc.core.RowMapper;

public class SampleSignalRowMapper implements RowMapper<Signal> {

	private Map<Integer, Sample> samples;
	
	public SampleSignalRowMapper(Map<Integer, Sample> samples) {
		this.samples = samples;
	}
	
	@Override
	public Signal mapRow(ResultSet rs, int rowNum) throws SQLException {
		Signal signal = new Signal();
		signal.setBssid(rs.getString("position_accesspoint_bssid"));
		signal.setSignalStrength(rs.getInt("signal_strength"));
		signal.setSampleId(rs.getInt("position_sample_id"));

		if (samples.containsKey(signal.getSampleId())) {
			samples.get(signal.getSampleId()).getSignalList().add(signal);
		} else {
			Sample sample = new Sample();
			sample.setId(signal.getSampleId());
			sample.setRoomId(rs.getInt("sample.position_room_id"));
			sample.getSignalList().add(signal);
			samples.put(signal.getSampleId(), sample);
		}
	
		return signal;
	}

}
