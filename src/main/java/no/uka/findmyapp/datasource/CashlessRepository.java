package no.uka.findmyapp.datasource;

import java.util.Date;
import java.util.List;

import no.uka.findmyapp.datasource.mapper.CashlessInvoiceItemRowMapper;
import no.uka.findmyapp.datasource.mapper.CashlessInvoiceRowMapper;
import no.uka.findmyapp.model.cashless.CashlessCard;
import no.uka.findmyapp.model.cashless.CashlessInvoice;
import no.uka.findmyapp.model.cashless.CashlessInvoiceItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CashlessRepository {

	@Autowired
	private JdbcTemplate mssqlJdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public CashlessCard getCardTransactions(long cardNo, Date from, Date to, String place) {
		
		CashlessCard card = new CashlessCard(cardNo);
		List<CashlessInvoice> invoices;
		
		// If no location provided
		if(place == null) {
			invoices = mssqlJdbcTemplate.query(
					"SELECT * FROM Invoice WHERE EventCardSerialNo=? AND [Invoice Date]> ? AND [Invoice Date]< ? ORDER BY [Invoice Date] DESC",
					new CashlessInvoiceRowMapper(), cardNo, from, to);
		}
		// Else specify location as well
		else {
			invoices = mssqlJdbcTemplate.query(
					"SELECT * FROM Invoice WHERE EventCardSerialNo=? AND [Invoice Date]> ? AND [Invoice Date]< ? AND Location LIKE ? ORDER BY [Invoice Date] DESC",
					new CashlessInvoiceRowMapper(), cardNo, from, to, place);
		}
		
		for(CashlessInvoice i : invoices){
			List<CashlessInvoiceItem> items = mssqlJdbcTemplate.query(
					"SELECT * FROM SIDetails WHERE [Invoice No]=?",
					new CashlessInvoiceItemRowMapper(),i.getInvoiceNo());
			i.setProducts(items);
		}
		
		card.setTransactions(invoices);
		return card;
	}
	
	public boolean updateCardNumber(int userId, long cardNo){
		try {
			// Set old cards to be invalid
			jdbcTemplate.update("UPDATE USER_CASHLESS SET in_use=0 WHERE user_id=?", userId);
			
			// Update/insert Cashless card number
			jdbcTemplate.update("REPLACE INTO USER_CASHLESS(user_id, card_no, in_use) VALUES( ? , ?, 1 )", userId, cardNo);
			
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public long getCardNumberFromUserId(int userId){
		try{
			return jdbcTemplate.queryForLong("SELECT card_no FROM USER_CASHLESS WHERE in_use=1 AND user_id=?", userId);
		}
		catch (Exception e) {
			return -1;
		}
	}
	
	public List<String> getCaslessLocations(){
		return mssqlJdbcTemplate.queryForList("SELECT DISTINCT Location FROM Invoice", String.class);
	}
	
	public int getCardBalance(long cardNo){
		try{
			return mssqlJdbcTemplate.queryForInt("SELECT TOP 1 EventCardBalance FROM Invoice WHERE EventCardSerialNo = ? ORDER BY [Invoice Date] DESC", cardNo);
		}
		catch (Exception e) {
			return 0;
		}
	}
}
