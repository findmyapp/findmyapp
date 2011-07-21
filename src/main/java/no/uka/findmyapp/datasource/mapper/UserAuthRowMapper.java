package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.auth.UserAuthInfo;

import org.springframework.jdbc.core.RowMapper;

public class UserAuthRowMapper implements RowMapper<UserAuthInfo> {

	public UserAuthInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserAuthInfo uai = new UserAuthInfo();
		uai.setTokenIssued(rs.getLong("token_issued"));
		return uai;
	}

}
