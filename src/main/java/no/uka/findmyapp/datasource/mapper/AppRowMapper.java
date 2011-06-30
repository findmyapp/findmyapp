package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.AppStore.App;

import org.springframework.jdbc.core.RowMapper;

public class AppRowMapper implements RowMapper<App> {
	@Override
	public App mapRow(ResultSet rs, int rowNum) throws SQLException {
		App app = new App();
		app.setAndroidMarketUri(rs.getString("marketIdentifier"));
		app.setName(rs.getString("name"));
		
		return app;
	}
}
