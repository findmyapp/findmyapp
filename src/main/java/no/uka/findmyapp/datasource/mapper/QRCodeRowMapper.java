package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.QRCode;

import org.springframework.jdbc.core.RowMapper;

public class QRCodeRowMapper implements RowMapper<QRCode> {

	@Override
	public QRCode mapRow(ResultSet rs, int rowNum) throws SQLException {
		QRCode qrCode = new QRCode();
		qrCode.setCode(rs.getString("code"));
		qrCode.setFromDate(rs.getTimestamp("from_date"));
		qrCode.setToDate(rs.getTimestamp("to_date"));
		qrCode.setSessionID(rs.getInt("session_id"));
		qrCode.setUses(rs.getInt("uses"));
		qrCode.setUnlimited(rs.getBoolean("unlimited"));
		return qrCode;
	}

}
