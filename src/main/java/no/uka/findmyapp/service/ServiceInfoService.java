package no.uka.findmyapp.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class ServiceInfoService {
	
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
	
	private List<ServiceModel> parseController(Class clazz) throws URISyntaxException {
		Annotation[] as =  clazz.getAnnotations();
		String controllerLocationPrefix = "";
		List<ServiceModel> list = new ArrayList<ServiceModel>();
		for(Annotation a : as) {
			if(a.annotationType() == RequestMapping.class) {
				//System.out.println("isRM");
				//System.out.println(a);
				RequestMapping req = (RequestMapping)a;
				//System.out.println(req.value()[0]);
				controllerLocationPrefix = req.value()[0];
			}
		}
		System.out.println(clazz.getSimpleName());
		for(Method m : clazz.getDeclaredMethods()) {

			ServiceModelMapping smm = m.getAnnotation(ServiceModelMapping.class);
			RequestMapping req = m.getAnnotation(RequestMapping.class);
			if(smm != null && req != null) {
				
				String location = replaceInLocationString(req.value()[0]);
				String requestType = req.method()[0].toString();
				String localIdentifier = m.getName();
				String controllerName = clazz.getSimpleName().replace("Controller", "");
				
				System.out.println(requestType);
				System.out.println(localIdentifier);
				System.out.println(controllerName);
				System.out.println(controllerLocationPrefix + location);
				smm.returnType();
				list.add(new ServiceModel(new URI("http://10.0.2.2:8080/findmyapp/" + location),
						HttpType.valueOf(requestType), 
						ServiceDataFormat.JSON, 
						smm.returnType(), 
						null, 
						new URI("no.uka.findmyapp.android.rest.providers/" + controllerName), 
						"no.uka.findmyapp.android.demo.BROADCAST_INTENT_TOKEN", 
						localIdentifier));
				
			}
		}
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
