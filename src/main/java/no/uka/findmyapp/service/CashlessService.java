package no.uka.findmyapp.service;

import java.util.Date;
import java.util.List;

import no.uka.findmyapp.configuration.UkaProgramConfiguration;
import no.uka.findmyapp.configuration.UkaProgramConfigurationList;
import no.uka.findmyapp.datasource.CashlessRepository;
import no.uka.findmyapp.exception.UkaYearNotFoundException;
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
	
	@Autowired
	private	UkaProgramConfigurationList ukaProgramConfigurationList;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
	.getLogger(CashlessService.class);
	
	public CashlessCard getCardTransactions(String ukaYear, int userId, Date date, Date from, Date to, String place) 
				throws UkaYearNotFoundException {
		
		long cardNo = getCardNumberFromUserId(userId);
		
		// If user don't have a Cashless card, return null
		if(cardNo == -1)
			return null;
		
		UkaProgramConfiguration config = ukaProgramConfigurationList.get(ukaYear);
		if (config == null) {
			throw new UkaYearNotFoundException("ukaYear "+ukaYear+" not found ");
		}
		if (date != null) {
			from = date;
			to = new Date(date.getTime()+ 86400000); // to =  (date+24h) -- (24*60*60*1000)
		}
		
		from = getFromDate(config, from);
		to = getToDate(config, to);

		return data.getCardTransactions(cardNo, from, to, place);
	}
	
	/**
	 * Get the Cashless card number from given userId
	 * 
	 * @param userId ID of user
	 * @return Cashless card number, -1 if no card exists
	 */
	public long getCardNumberFromUserId(int userId){
		return data.getCardNumberFromUserId(userId);
	}
	
	/**
	 * Updates the given user with the given Cashless number
	 * 
	 * @param userId ID of user
	 * @param cardNo Cashless card number
	 * @return true if successful update, else false
	 */
	public boolean updateCardNumber(int userId, long cardNo){
		return data.updateCardNumber(userId, cardNo);
	}
	
	public List<String> getCashlessLocations(){
		return data.getCaslessLocations();
	}
	
	/**
	 * Private method for getting the last date of arguments
	 * @param config 
	 * @param date
	 * @return
	 */
	private Date getFromDate(UkaProgramConfiguration config, Date date) {
		if (date == null || config.getStartDate().after(date))
			return config.getStartDate();
		return date;
	}
	/**
	 * Private method for getting the first date of arguments
	 * @param config 
	 * @param date
	 * @return
	 */
	private Date getToDate(UkaProgramConfiguration config, Date date) {
		if (date == null || config.getEndDate().before(date))
			return config.getEndDate();
		return date;
	}
}
