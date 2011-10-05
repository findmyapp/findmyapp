package no.uka.findmyapp.datasource;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.QRCodeRowMapper;
import no.uka.findmyapp.model.QRCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QRCodeRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;
	
	public QRCode getQRCode(String code){
		return jdbcTemplate.queryForObject("Select * from QR_CODE WHERE code=?", new QRCodeRowMapper(), code);
	}
	
	public Boolean decrementUsesOnQRCode(String code){
		return jdbcTemplate.update("UPDATE QR_CODE SET uses=uses-1 WHERE code=?", code)>0;
	}
	
	public boolean hasQRCode(String code){
		int n = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM QR_CODE WHERE code=?", code);
		return (n>0);
	}
}
