<%@ page import="java.util.Enumeration, no.uka.findmyapp.configuration.GsonJsonConfiguration, com.google.gson.Gson;" %>
<% 
Enumeration<String> names = request.getAttributeNames(); 
GsonJsonConfiguration configs = new GsonJsonConfiguration();
Gson gson = configs.createGson();
String name;
String value;
while (names.hasMoreElements()) { 
	name = names.nextElement();
	if (name.indexOf(".springframework.") < 0 && name.indexOf(".servlet.") < 0 && name.indexOf("_spring_security_") < 0) { 
		value = gson.toJson(request.getAttribute(name));
		if (value.indexOf("{") < 0 && value.indexOf("[") < 0) {//primitiv datatype
			out.print("{\""+name+"\":"+value+"}");
		}
		else {
			out.print(value);
		}
	}	
}%>

