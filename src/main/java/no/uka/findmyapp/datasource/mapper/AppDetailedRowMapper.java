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
		app.setMarketID(rs.getString("market_identifier"));
		app.setName(rs.getString("name"));
		app.setCategory(rs.getString("category"));
		app.setDescription(rs.getString("description"));
		app.setPlatform(rs.getString("platform"));
		developer.setDeveloperName(rs.getString("fullname"));
		try {
			app.setThumbImage(new URI(rs.getString(("thumb_image"))));
		} catch (URISyntaxException e) {
			app.setThumbImage(null);
		}
		app.setDeveloper(developer);
		return app;
	}
}
