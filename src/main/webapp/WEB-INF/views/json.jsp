<%@ page import="java.util.Enumeration, no.uka.findmyapp.configuration.GsonJsonConfiguration, com.google.gson.Gson;" %>
<% Enumeration<String> names = request.getAttributeNames(); 
GsonJsonConfiguration configs = new GsonJsonConfiguration();
Gson gson = configs.createGson();
String name; %>
{
<% while (names.hasMoreElements()) { 
	name = names.nextElement();
	if (name.indexOf(".springframework.") < 0 && name.indexOf(".servlet.") < 0) { %>
		"<%= name %>":<%= gson.toJson(request.getAttribute(name)) %>
	<% }	
} %>
}