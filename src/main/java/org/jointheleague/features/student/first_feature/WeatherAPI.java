package org.jointheleague.features.student.first_feature;

import java.util.Random;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.ApiExampleWrapper;
import org.jointheleague.features.templates.FeatureTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class WeatherAPI extends FeatureTemplate{

public final String COMMAND = "weather";
public final String COMMAND_ = "raw_weather";
	
	private static final String key = "7a59fc69444d4617a5a225318250309";
    private static final String URL = "http://api.weatherapi.com/v1/current.json";

    private  WebClient webClient = WebClient.create(URL);
	
	public WeatherAPI(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
		
		this.webClient = WebClient
                .builder()
                .baseUrl(URL)
                .build();
	}
	
	

    public String getCurrentWeather(String city) {
    	
    	Mono<String> apiExampleWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", city)
                        .queryParam("key", key)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    	
    	return apiExampleWrapperMono.block();

    }
    

    /*public void printCurrentWeather(String city) {
    	
    	Mono<String> apiExampleWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", city)
                        .queryParam("key", key)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    	
    	System.out.println( apiExampleWrapperMono.block());

    }*/
	
	public void handle(ReceivedMessage event) {
		String mc = event.getMessageContent();
		if(mc.trim().toLowerCase().equals(COMMAND)||mc.trim().toLowerCase().equals(COMMAND_)) {
			event.sendResponse("Please add a city.");
		}

		if(mc.trim().toLowerCase().startsWith(COMMAND)) {
			
			String city = mc.trim().substring(COMMAND.length()+1);
		
			String json = getCurrentWeather(city);
			
			//event.sendResponse(json);
			
			String tempKey = "\"temp_f\":";
	        int tempStart = json.indexOf(tempKey) + tempKey.length();
	        int tempEnd = json.indexOf(",", tempStart);
	        String temp = json.substring(tempStart, tempEnd);

	        // Extract humidity
	        String humidityKey = "\"humidity\":";
	        int humidityStart = json.indexOf(humidityKey) + humidityKey.length();
	        int humidityEnd = json.indexOf(",", humidityStart);
	        String humidity = json.substring(humidityStart, humidityEnd);
	        
	        String iconKey = "\"icon\":\"";
	        int iconStart = json.indexOf(iconKey) + iconKey.length();
	        int iconEnd = json.indexOf("\"", iconStart);
	        String icon = json.substring(iconStart, iconEnd);
	        
	        String regionKey = "\"region\":\"";
	        int regionStart = json.indexOf(regionKey) + regionKey.length();
	        int regionEnd = json.indexOf("\"", regionStart);
	        String region = json.substring(regionStart, regionEnd);

	        String countryKey = "\"country\":\"";
	        int countryStart = json.indexOf(countryKey) + countryKey.length();
	        int countryEnd = json.indexOf("\"", countryStart);
	        String country = json.substring(countryStart, countryEnd);
	        
	        event.sendResponse("__Conditions for " + city + ", "+country+".__" );
	        event.sendResponse("Temperature (F): "+temp);
	        event.sendResponse("Humidity (%): "+humidity);
	        event.sendResponse("https:"+icon);
	        
			
			// TODO: Make a thread run this code so that I can have an external Timer 
	        // and after some time has elapsed interrupt and say "city not found" 
			
		}
		if(mc.trim().toLowerCase().startsWith(COMMAND_)) {
			String city = mc.trim().substring(COMMAND.length()+1);
			
			String json = getCurrentWeather(city);
			event.sendResponse(json);
		}
    }
	
	
	
	
	
}
