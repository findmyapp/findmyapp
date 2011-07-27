package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.cashless.CashlessInvoiceItem;

import org.springframework.jdbc.core.RowMapper;

public class CashlessInvoiceItemRowMapper implements RowMapper<CashlessInvoiceItem> {


	public CashlessInvoiceItem mapRow(ResultSet rs, int arg1) throws SQLException {
		CashlessInvoiceItem item = new CashlessInvoiceItem();
		item.setProductId(rs.getString(2)); // Product Id
		item.setProductName(rs.getString("ProductName"));
		item.setQuantitySold(rs.getInt("QtySold"));
		item.setTotalAmount(rs.getInt("TAmount"));
		return item;
	}

}