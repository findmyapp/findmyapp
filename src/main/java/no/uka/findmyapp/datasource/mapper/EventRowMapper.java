package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.UkaEvent;

import org.springframework.jdbc.core.RowMapper;

public class EventRowMapper implements RowMapper<UkaEvent> {


	public UkaEvent mapRow(ResultSet rs, int arg1) throws SQLException {
		UkaEvent event = new UkaEvent();
		event.setId(rs.getInt("id"));//returnerer id fra event_showing_real
		event.setShowingTime(rs.getTimestamp("showing_time"));
		event.setPlace(rs.getString("place"));
		event.setPlaceString(rs.getString("place_string"));
		event.setBilligId(rs.getInt("billig_id"));
		event.setFree(rs.getBoolean("free"));
		event.setCanceled(rs.getBoolean("canceled"));
		event.setTitle(rs.getString("title"));
		event.setLead(rs.getString("lead"));
		event.setText(rs.getString("text"));
		event.setEventType(rs.getString("event_type"));
		event.setImage(rs.getString("image"));
		event.setThumbnail( rs.getString("thumbnail") );
		event.setAgeLimit( rs.getInt("age_limit") );
		event.setLowestPrice( rs.getInt("lowest_price") );
		event.setEventId(rs.getLong("event_id"));
		//event.setPublishTime(rs.getTimestamp("publish_time"));
		//event.setNetsaleFrom(rs.getTimestamp("netsale_from"));
		//event.setNetsaleTo(rs.getTimestamp("netsale_to"));
		//event.setEntranceId(rs.getInt("entrance_id"));
		//event.setHiddenFromListing(rs.getBoolean("hidden_from_listing"));
		//event.setSlug(rs.getString("slug"));
		//event.setDetailPhotoId(rs.getInt("detail_photo_id"));
		return event;
	}

}