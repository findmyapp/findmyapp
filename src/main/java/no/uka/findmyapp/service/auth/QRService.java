package no.uka.findmyapp.service.auth;

import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.uka.findmyapp.datasource.QRCodeRepository;
import no.uka.findmyapp.model.QRCode;
import no.uka.findmyapp.service.FacebookService;

@Service
public class QRService {
	private static final Logger logger = LoggerFactory
			.getLogger(FacebookService.class);

	@Autowired
	private QRCodeRepository qrCodeRepository;

	public int verify(String code, int sessionID){
		if (!qrCodeRepository.hasQRCode(code)){
			logger.debug(code + " does not exist in the database");
			return -1;
		}
		QRCode qr = qrCodeRepository.getQRCode(code);
		logger.debug("QRCode data: "+ qr.getCode() +" "+qr.getSessionID()+ " " +qr.getUses()+ " "+qr.getFromDate()+ " " + qr.getToDate() + " "+qr.getUnlimited());

		//Check if the code is for this place
		if (qr.getSessionID()!=-1){
			if (!(qr.getSessionID()==sessionID)){
				logger.debug("QRCode: "+code + " is not is not for session: "+sessionID);
				return -1;
			}
		} else {
			logger.debug("QRCode: "+code + " have no specific session");
		}
		if (!isQRCodeValidAtCurrentTime(qr)){
			return -1;
		}
		if (!qr.getUnlimited() && qr.getUses()<=0){
			return -1;
		}
		return 1;
	}

	public boolean codeIsUsed(String code){
		QRCode qr = qrCodeRepository.getQRCode(code);
		if (qr.getUnlimited()){
			logger.debug("Code has unlimited uses");
			return true;
		}

		if (qrCodeRepository.decrementUsesOnQRCode(code)){
			logger.debug("Successful drecrement of uses");
			return true;
		}
		logger.debug("Unsuccessful drecrement of uses");
		return false;
	}

	private boolean isQRCodeValidAtCurrentTime(QRCode code){
		Timestamp now = new Timestamp(System.currentTimeMillis());
		if (code.getFromDate()==null && code.getToDate()==null){
			logger.debug("No time period on code");
			return true;
		}
		if (code.getFromDate()==null && code.getToDate()!=null){
			if (now.before(code.getToDate())){
				logger.debug("Code valid to "+code.getToDate().toString());
			}
			return (now.before(code.getToDate()));
		}
		if (code.getFromDate()!=null && code.getToDate()==null){
			if (now.after(code.getFromDate())){
				logger.debug("Code valid from "+code.getFromDate().toString());
			}
			return (now.after(code.getFromDate()));
		}
		logger.debug("Code valid from "+code.getFromDate().toString() + " to "+ code.getToDate().toString());
		return (now.after(code.getFromDate()) && now.before(code.getToDate()));
	}

}
