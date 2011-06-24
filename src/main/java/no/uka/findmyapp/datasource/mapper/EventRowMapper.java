package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import no.uka.findmyapp.model.Event;
import org.springframework.jdbc.core.RowMapper;

public class EventRowMapper implements RowMapper<Event> {


	public Event mapRow(ResultSet rs, int arg1) throws SQLException {
		System.out.println("-------- event lagt til ");
		Event event = new Event();
		event.setId(rs.getInt("id"));
		event.setTitle(rs.getString("title"));
		event.setLead(rs.getString("lead"));
		event.setDescription(rs.getString("description"));
		event.setPicture(rs.getString("picture"));
		event.setThumbnail(rs.getString("thumbnail"));
		event.setUrl(rs.getString("url"));
		event.setEventType(rs.getString("event_type"));
		event.setLocation(rs.getString("location"));
		event.setTicketPrice(rs.getString("ticket_price"));
		event.setDate(rs.getDate("event_date"));
		
		return event;
	}

}
