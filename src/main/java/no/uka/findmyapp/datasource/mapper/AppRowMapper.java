package no.uka.findmyapp.datasource.mapper;

import java.net.URI;
import java.net.URISyntaxException;
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
		try {
			app.setThumbImage(new URI(rs.getString(("thumb_image"))));
		} catch (URISyntaxException e) {
			app.setThumbImage(null);
		} 
		return app;
	}
}
