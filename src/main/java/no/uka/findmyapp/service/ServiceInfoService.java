package no.uka.findmyapp.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import no.uka.findmyapp.controller.AppStoreController;
import no.uka.findmyapp.controller.LocationController;
import no.uka.findmyapp.controller.PingController;
import no.uka.findmyapp.controller.SensorController;
import no.uka.findmyapp.controller.UkaProgramController;
import no.uka.findmyapp.controller.UserController;
import no.uka.findmyapp.helpers.ServiceModelMapping;
import no.uka.findmyapp.model.serviceinfo.HttpType;
import no.uka.findmyapp.model.serviceinfo.ServiceDataFormat;
import no.uka.findmyapp.model.serviceinfo.ServiceModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class ServiceInfoService {

	private static final Logger logger = LoggerFactory
	.getLogger(ServiceInfoService.class);
	
	public List<ServiceModel> getAllServices() throws URISyntaxException {
		Class controller = SensorController.class;
		List<ServiceModel> list = parseController(controller);
		list.addAll(parseController(AppStoreController.class));
		list.addAll(parseController(LocationController.class));
		list.addAll(parseController(PingController.class));
		list.addAll(parseController(UkaProgramController.class));
		list.addAll(parseController(UserController.class));
		
		return list;
	}
	

	public List<String> getAllServicesFact() throws URISyntaxException {
		Class controller = SensorController.class;
		List<String> list = parseControllerFact(controller);
		list.addAll(parseControllerFact(AppStoreController.class));
		list.addAll(parseControllerFact(LocationController.class));
		list.addAll(parseControllerFact(PingController.class));
		list.addAll(parseControllerFact(UkaProgramController.class));
		list.addAll(parseControllerFact(UserController.class));
		logger.info("size : " + list.size());
		return list;
	}
	
	private List<ServiceModel> parseController(Class clazz) throws URISyntaxException {
		Annotation[] as =  clazz.getAnnotations();
		String controllerLocationPrefix = "";
		List<ServiceModel> list = new ArrayList<ServiceModel>();
		for(Annotation a : as) {
			if(a.annotationType() == RequestMapping.class) {
				RequestMapping req = (RequestMapping)a;
				if(req.value().length > 0) {
					controllerLocationPrefix = req.value()[0];
				}
			}
		}
		
		for(Method m : clazz.getDeclaredMethods()) {
			
			ServiceModelMapping smm = m.getAnnotation(ServiceModelMapping.class);
			RequestMapping req = m.getAnnotation(RequestMapping.class);
			
			if(smm != null && req != null) {
				String location = "";
				if(req.value().length > 0) {
					location = replaceInLocationString(req.value()[0]);
				}
				
				String requestType = req.method()[0].toString();
				String localIdentifier = m.getName();
				String controllerName = clazz.getSimpleName().replace("Controller", "");
				Class returnType = smm.returnType();
				//new URI("no.uka.findmyapp.android.rest.providers." + controllerName.toLowerCase() + "/" + returnType.getSimpleName().toLowerCase()), 
				
				list.add(new ServiceModel(new URI("http://findmyapp.net/findmyapp/" + controllerLocationPrefix + location),
						HttpType.valueOf(requestType), 
						ServiceDataFormat.JSON, 
						returnType, 
						null, 
						null,
						"no.uka.findmyapp.android.demo.BROADCAST_INTENT_TOKEN", 
						localIdentifier));
				//new URI("no.uka.findmyapp.android.rest.providers/" + returnType.getSimpleName().toLowerCase()), 
			}
		}
		return list;
	}
	
	private List<String> parseControllerFact(Class clazz) throws URISyntaxException {
		Annotation[] as =  clazz.getAnnotations();
		String controllerLocationPrefix = "";
		List<String> list = new ArrayList<String>();
		for(Annotation a : as) {
			if(a.annotationType() == RequestMapping.class) {
				RequestMapping req = (RequestMapping)a;
				if(req.value().length > 0) {
					controllerLocationPrefix = req.value()[0];
				}
			}
		}
		String idents = "";
		for(Method m : clazz.getDeclaredMethods()) {
			
			ServiceModelMapping smm = m.getAnnotation(ServiceModelMapping.class);
			RequestMapping req = m.getAnnotation(RequestMapping.class);
			
			if(smm != null && req != null) {
				String location = "";
				if(req.value().length > 0) {
					location = replaceInLocationString(req.value()[0]);
				}
				
				String requestType = req.method()[0].toString();
				String localIdentifier = m.getName();
				String controllerName = clazz.getSimpleName().replace("Controller", "");
				Class returnType = smm.returnType();
				//new URI("no.uka.findmyapp.android.rest.providers." + controllerName.toLowerCase() + "/" + returnType.getSimpleName().toLowerCase()), 
				
				String s = "case " +localIdentifier.toUpperCase()+ ": --- model = new ServiceModel(new URI(\"http://findmyapp.net/findmyapp" + controllerLocationPrefix + location+"\")," +
						"HttpType." + HttpType.valueOf(requestType) + "," +  
						"ServiceDataFormat.JSON," +  
						returnType + "," + 
						"null," +
						"null," +
						"\"no.uka.findmyapp.android.demo.BROADCAST_INTENT_TOKEN\"," + 
						"\"" + localIdentifier+ "\"); --- break;";
				list.add(s);
				idents += "," + localIdentifier.toUpperCase();
				//new URI("no.uka.findmyapp.android.rest.providers/" + returnType.getSimpleName().toLowerCase()), 
			}
		}
		list.add(idents);
		return list;
	}
	
	private String replaceInLocationString(String orig) {
		int startBegin = orig.indexOf("{");
		while(startBegin > -1) {
			if(startBegin > -1) {
				int startEnd = orig.indexOf("}") + 1;
				String strToReplace = orig.substring(startBegin, startEnd);
				orig = orig.replace(strToReplace, "?");
				startBegin = orig.indexOf("{");
			}
		}
		return orig;
	}
	
	
	
}
