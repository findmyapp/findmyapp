package no.uka.findmyapp.datasource;

import java.util.List;

import no.uka.findmyapp.datasource.mapper.CashlessInvoiceItemRowMapper;
import no.uka.findmyapp.datasource.mapper.CashlessInvoiceRowMapper;
import no.uka.findmyapp.model.cashless.CashlessCard;
import no.uka.findmyapp.model.cashless.CashlessInvoice;
import no.uka.findmyapp.model.cashless.CashlessInvoiceItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CashlessRepository {

	@Autowired
	private JdbcTemplate mssqlJdbcTemplate;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory
	.getLogger(CashlessRepository.class);
	
	public CashlessCard getCardTransactions(long cardNo) {
		//logger.debug("In the cashless dataSource");
		System.out.println("In the cashless dataSource");
		
		CashlessCard card = new CashlessCard(cardNo);
		
		//return mssqlJdbcTemplate.queryForLong("select top 1 [Invoice No] from Invoice");
		List<CashlessInvoice> invoices = mssqlJdbcTemplate.query(
				"SELECT * FROM Invoice WHERE EventCardSerialNo=? ORDER BY [Invoice Date] DESC",
				new CashlessInvoiceRowMapper(),cardNo);
		
		for(CashlessInvoice i : invoices){
			List<CashlessInvoiceItem> items = mssqlJdbcTemplate.query(
					"SELECT * FROM SIDetails WHERE [Invoice No]=?",
					new CashlessInvoiceItemRowMapper(),i.getInvoiceNo());
			i.setProducts(items);
			
			// MAKE SURE THIS IS CORRECT!!!
			card.setBalance(i.getCardBalanceAfter());
		}
		
		card.setTransactions(invoices);
		return card;
	}
	
	public boolean updateCardNumber(int userId, long cardNo){
		jdbcTemplate.update("REPLACE INTO USER_CASHLESS(user_id, card_no) VALUES( ? , ? )",
				userId, cardNo);
		
		return true;
	}
	
	public long getCardNumberFromUserId(int userId){
		try{
			return jdbcTemplate.queryForLong("SELECT card_no FROM USER_CASHLESS WHERE user_id=?", userId);
		}
		catch (Exception e) {
			return -1;
		}
	}
}
