package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.appstore.App;
import no.uka.findmyapp.model.appstore.AppDetailed;
import no.uka.findmyapp.model.appstore.Developer;

import org.springframework.jdbc.core.RowMapper;

public class AppDetailedRowMapper implements RowMapper<AppDetailed> {
	
	public AppDetailed mapRow(ResultSet rs, int rowNum) throws SQLException {
		AppDetailed app = new AppDetailed();
		Developer developer = new Developer();
		app.setMarketID(rs.getString("market_identifier"));
		app.setName(rs.getString("name"));
		app.setCategory(rs.getString("category"));
		app.setDescription(rs.getString("description"));
		app.setPlatform(rs.getInt("platform"));
		app.setFacebookAppID(rs.getString("facebook_app_id"));	
		developer.setDeveloperName(rs.getString("fullname"));
		app.setDeveloper(developer);
		return app;
	}
}
