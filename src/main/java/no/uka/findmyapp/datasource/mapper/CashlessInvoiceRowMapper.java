package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.cashless.CashlessInvoice;

import org.springframework.jdbc.core.RowMapper;

public class CashlessInvoiceRowMapper implements RowMapper<CashlessInvoice> {


	public CashlessInvoice mapRow(ResultSet rs, int arg1) throws SQLException {
		CashlessInvoice invoice = new CashlessInvoice();
		invoice.setInvoiceNo(rs.getString(1)); // [Invoice No]
		invoice.setLocation(rs.getString("UserName")); // This must be changed
		invoice.setSaleTime(rs.getTimestamp(6));
		return invoice;
	}

}