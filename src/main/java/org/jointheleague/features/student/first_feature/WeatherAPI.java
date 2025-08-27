package org.jointheleague.features.student.first_feature;

import java.util.Random;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.ApiExampleWrapper;
import org.jointheleague.features.templates.FeatureTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class WeatherAPI extends FeatureTemplate{

public final String COMMAND = "weather";
	
	private static final String key = "65e242ae27dd4a83a3d224705252708";
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
	
	

    public CurrentWeather getCurrentWeather(String city) {
    	
    	Mono<CurrentWeather> apiExampleWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", city)
                        .queryParam("key", key)
                        .build())
                .retrieve()
                .bodyToMono(CurrentWeather.class);
    	
    	return apiExampleWrapperMono.block();

    }
    

    public void printCurrentWeather(String city) {
    	
    	Mono<String> apiExampleWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", city)
                        .queryParam("key", key)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    	
    	System.out.println( apiExampleWrapperMono.block());

    }
	
	public void handle(ReceivedMessage event) {
		String mc = event.getMessageContent();
		if(mc.trim().toLowerCase().equals(COMMAND)) {
			event.sendResponse("Please add a city.");
		}
		
		if(mc.trim().toLowerCase().startsWith(COMMAND)) {
			
			String city = mc.trim().substring(COMMAND.length()+2);
			System.out.println("before block"); // debug
			printCurrentWeather(city);
			CurrentWeather data = getCurrentWeather(city);
			System.out.println("after block"); // debug
			
			String temp = data.getCurrent().getTempC()+"";
			//String hum = data.getCurrent().getHumidity()+"";
			System.out.println(data);
			
			event.sendResponse("Temperature (F):" + temp);
		//	event.sendResponse("Humidity (%): " + hum);
			
			
			// do the cumbersome block(String.class).substring() method to get values when blocking to string instead of wrapper
			
		}
    }
	
	
	
	
	
}
