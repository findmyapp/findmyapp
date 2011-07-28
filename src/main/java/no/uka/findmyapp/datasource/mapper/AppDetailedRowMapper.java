package no.uka.findmyapp.datasource.mapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.appstore.AppDetailed;
import no.uka.findmyapp.model.appstore.Developer;

import org.springframework.jdbc.core.RowMapper;

public class AppDetailedRowMapper implements RowMapper<AppDetailed> {
	
	public AppDetailed mapRow(ResultSet rs, int rowNum) throws SQLException {
		AppDetailed app = new AppDetailed();
		Developer developer = new Developer();
		app.setId(rs.getInt("appstore_application_id"));
		app.setMarketID(rs.getString("market_identifier"));
		app.setName(rs.getString("name"));
		app.setCategory(rs.getString("category"));
		app.setDescription(rs.getString("description"));
		app.setPlatform(rs.getString("platform"));	
		app.setActivated(rs.getBoolean("activated"));
		app.setRemoved(rs.getBoolean("removed"));
		app.setFacebookAppID(rs.getString("facebook_app_id"));
		app.setFacebookSecret(rs.getString("facebook_secret"));
		app.setConsumerKey(rs.getString("consumer_key"));
		app.setConsumerSecret(rs.getString("consumer_secret"));
		try {
			app.setThumbImage(new URI(rs.getString(("thumb_image"))));
		} catch (URISyntaxException e) {
			app.setThumbImage(null);
		} 
		app.setDeveloper(developer);
		return app;
	}
}
