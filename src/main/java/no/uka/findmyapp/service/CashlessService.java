package no.uka.findmyapp.service;

import no.uka.findmyapp.datasource.CashlessRepository;
import no.uka.findmyapp.model.cashless.CashlessCard;

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
	
	public CashlessCard getCardTransactions(int userId) {
		
		long cardNo = getCardNumberFromUserId(userId);
		
		if(cardNo != -1)
			return data.getCardTransactions( cardNo );
		else
			return null;
	}
	
	public long getCardNumberFromUserId(int userId){
		return data.getCardNumberFromUserId(userId);
	}
	
	public boolean updateCardNumber(int userId, long cardNo){
		return data.updateCardNumber(userId, cardNo);
	}
}
