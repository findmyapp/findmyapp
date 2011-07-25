package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.appstore.App;

import org.springframework.jdbc.core.RowMapper;

public class AppRowMapper implements RowMapper<App> {
	
	public App mapRow(ResultSet rs, int rowNum) throws SQLException {
		App app = new App();
		app.setMarketID(rs.getString("market_identifier"));
		app.setName(rs.getString("name"));
		app.setCategory(rs.getString("category"));
		app.setDescription(rs.getString("description"));
		app.setPlatform(rs.getString("platform"));
		app.setFacebookAppID(rs.getString("facebook_app_id"));	
		app.setDeveloperName(rs.getString("fullname"));
		app.setFacebookSecret(rs.getString("facebook_secret"));
		return app;
	}
}
