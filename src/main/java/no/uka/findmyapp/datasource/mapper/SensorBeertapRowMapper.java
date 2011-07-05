package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import no.uka.findmyapp.model.Beertap;
import org.springframework.jdbc.core.RowMapper;

public class SensorBeertapRowMapper implements RowMapper<Beertap> {

	public Beertap mapRow(ResultSet rs, int arg1) throws SQLException {
		Beertap beertap =  new Beertap();
		beertap.setId(rs.getInt("sensor_beertap_id"));
		beertap.setTime(rs.getTimestamp("date"));
		beertap.setLocation(rs.getInt("location"));
		beertap.setValue(rs.getFloat("value"));
		beertap.setTapnr(rs.getInt("tapnr"));
		return beertap;
	}

}

