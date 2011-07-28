<%@ page import="java.util.Enumeration, no.uka.findmyapp.configuration.GsonJsonConfiguration, com.google.gson.Gson;" %>
<% 
Enumeration<String> names = request.getAttributeNames(); 
GsonJsonConfiguration configs = new GsonJsonConfiguration();
Gson gson = configs.createGson();
String name;
String value;
while (names.hasMoreElements()) { 
	name = names.nextElement();
	if (name.indexOf("_") < 0 && name.indexOf(".") < 0) { 
		value = gson.toJson(request.getAttribute(name));
		//if (value.indexOf("{") < 0 && value.indexOf("[") < 0) {//primitiv datatype
		//	out.print("{\""+name+"\":"+value+"}");
		//}
		//else {
			out.print(value);
		//}
	}	
}%>

