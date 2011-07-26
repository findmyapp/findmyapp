package no.uka.findmyapp.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CashlessRepository {

	@Autowired
	private JdbcTemplate mssqlJdbcTemplate;

	private static final Logger logger = LoggerFactory
	.getLogger(CashlessRepository.class);
	
	public long testCashless() {
		//logger.debug("In the cashless dataSource");
		System.out.println("In the cashless dataSource");
		return mssqlJdbcTemplate.queryForLong("select [Invoice No] from Invoice");
		/*List<UkaEvent> eventList = mssqlJdbcTemplate.query(
				"SELECT * FROM UKA_EVENTS WHERE showing_time>=? AND showing_time<=?",
				new EventRowMapper(),startDate, endDate  );
		*/
		//return eventList;
	}
}
