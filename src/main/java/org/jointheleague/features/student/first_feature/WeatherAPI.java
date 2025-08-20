package org.jointheleague.features.student.first_feature;

import java.util.Random;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.ApiExampleWrapper;
import org.jointheleague.features.templates.FeatureTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class WeatherAPI extends FeatureTemplate{

public final String COMMAND = "weather";
	
	private static final String key = "82d1da6ca0b64c12ada224552252008";
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
                        .queryParam("apiKey", key)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    	
    	return apiExampleWrapperMono.block();

    }
	
	public void handle(ReceivedMessage event) {
		String mc = event.getMessageContent();
		if(mc.trim().toLowerCase().equals(COMMAND)) {
			event.sendResponse("Please add a city.");
		}
		
		if(mc.trim().toLowerCase().startsWith(COMMAND)) {
			
			String city = mc.trim().substring(8);
			System.out.println("before block"); // debug
			String temp = getCurrentWeather(city);
			System.out.println("after block"); // debug
			event.sendResponse(temp + " fsd fsdfsdfs");
		}
    }
	
	
	
	
	
}
