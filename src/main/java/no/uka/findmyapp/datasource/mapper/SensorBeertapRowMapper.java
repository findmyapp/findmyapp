package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import no.uka.findmyapp.model.BeerTap;
import org.springframework.jdbc.core.RowMapper;

public class SensorBeertapRowMapper implements RowMapper<BeerTap> {

	public BeerTap mapRow(ResultSet rs, int arg1) throws SQLException {
		BeerTap beerTap =  new BeerTap();
		beerTap.setId(rs.getInt("sensor_beertap_id"));
		beerTap.setTime(rs.getTimestamp("date"));
		beerTap.setLocation(rs.getInt("position_location_id"));
		beerTap.setValue(rs.getFloat("value"));
		beerTap.setTapnr(rs.getInt("tapnr"));
		return beerTap;
	}

}

