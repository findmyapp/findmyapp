package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.appstore.App;

import org.springframework.jdbc.core.RowMapper;

public class AppRowMapper implements RowMapper<App> {
	
	public App mapRow(ResultSet rs, int rowNum) throws SQLException {
		App app = new App();
		app.setAndroidMarketUri("details?id=" + rs.getString("market_identifier") + "\">");
		app.setName(rs.getString("name"));
		
		return app;
	}
}
