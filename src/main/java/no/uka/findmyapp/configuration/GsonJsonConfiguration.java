package no.uka.findmyapp.configuration;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
public class GsonJsonConfiguration {
	
	@Bean(autowire = Autowire.BY_TYPE)
	public Gson createGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd HH:mm");
		Gson gson = builder.create();
		return gson;
	}
	
}
