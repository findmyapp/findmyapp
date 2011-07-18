package no.uka.findmyapp.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import no.uka.findmyapp.exception.UkaYearNotFoundException;
import no.uka.findmyapp.helpers.ServiceModelMapping;
import no.uka.findmyapp.model.UkaProgram;
import no.uka.findmyapp.model.serviceinfo.HttpType;
import no.uka.findmyapp.model.serviceinfo.ServiceDataFormat;
import no.uka.findmyapp.model.serviceinfo.ServiceModel;
import no.uka.findmyapp.service.ServiceInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/serviceinfo")
public class ServiceInfoController {

	private static final Logger logger = LoggerFactory
	.getLogger(ServiceInfoController.class);
	
	@Autowired
	private ServiceInfoService serviceInfoService;
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();;
	

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String getServiceInfo(Model model) throws URISyntaxException {
		logger.info("getting service info for all services");
	 	List<ServiceModel> list = serviceInfoService.getAllServices();
	 	for(ServiceModel s : list) {
	 		System.out.println(s);
	 	}

		logger.info("returning : " + list.size() + " ServiceModels");
		model.addAttribute("json", gson.toJson(list));
		return "json";
	}
	
	
	public void Test() {
		
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	public void handleIllegalArgumentException(
			IllegalArgumentException ex) {
		logger.info("handleIllegalArgumentException ( "
				+ ex.getLocalizedMessage() + " )");
	}
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler
	private void handleEmptyResultDataAccessException(
			EmptyResultDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( "
				+ ex.getLocalizedMessage() + " )");
	}
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UkaYearNotFoundException.class)
	private void handleUkaYearNotFoundException(UkaYearNotFoundException e) {
		logger.info("UkaYearNotFoundException ( "+e.getLocalizedMessage()+ " )");
	}
}
