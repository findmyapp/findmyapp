package no.uka.findmyapp.service;

import no.uka.findmyapp.datasource.CashlessRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * This class is used between the UkaProgramController layer and the UkaProgramRepository layer. It contains most of the logic.
 */
@Service
public class CashlessService {
	
	@Autowired
	private CashlessRepository data;
	
	private static final Logger logger = LoggerFactory
	.getLogger(CashlessService.class);
	
	/**
	 * titleSearch searches for a list of events with names containing a substring close to the query.
	 * @param qry is the search query. 
	 * @return is a list of all the events sorted by relevance, wrapped inside UkaProgram. 
	 */
	public void testCashless() {
		logger.debug("Entering CashlessService");
		data.testCashless();
		logger.debug("Returning from CashlessService");
	}
}
