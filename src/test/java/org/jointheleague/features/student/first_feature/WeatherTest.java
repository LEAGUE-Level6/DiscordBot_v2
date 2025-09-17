package org.jointheleague.features.student.first_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;


import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.function.Function;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class WeatherTest {

	private final String testChannelName = "test";
	WeatherAPI test;
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    public final String COMMAND = "weather";
    public final String COMMAND_ = "raw_weather";
    
    @Mock
    public WebClient wc;
    
    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @Mock
    WebClient.ResponseSpec responseSpecMock;

    @Mock
    Mono<CurrentWeather> m_cw;
    
    @Mock
    ReceivedMessage rm;
    
    @Mock
    UriBuilder urib;

    //@Mock
    //URI uri;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outContent));
        test = new WeatherAPI(testChannelName, wc);
    }
    
    @Test
    void confirmProperResponse() {

    	//Given
        String city = "Bob";
        
        CurrentWeather cww = new CurrentWeather();
        Current c = new Current(); c.setTempF(37.5); c.setHumidity(30); Condition cond = new Condition(); cond.setIcon("//cdn.weatherapi.com/weather/64x64/day/122.png");c.setCondition(cond); cww.setCurrent(c);
        Location l = new Location(); l.setCountry("Boblandia"); cww.setLocation(l);
        
        //When
        when(wc.get()).thenReturn(requestHeadersUriSpecMock);
    	when(requestHeadersUriSpecMock.uri((Function<UriBuilder, URI>) any())).thenReturn(requestHeadersSpecMock);
    	when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
    	when(responseSpecMock.bodyToMono(CurrentWeather.class)).thenReturn(m_cw);

    	when(urib.queryParam("q", city)).thenReturn(urib);
    	when(urib.queryParam("key","7a59fc69444d4617a5a225318250309")).thenReturn(urib);
    	when(urib.build()).thenReturn(null);
    	
    	when(m_cw.block()).thenReturn(cww);
  
    	when(rm.getMessageContent()).thenReturn("weather Bob"); // change this
    	
    	test.handle(rm);
    	
        //Then
        
    	verify(rm,times(1)).sendResponse("__Conditions for Bob, Boblandia.__");
    	verify(rm,times(1)).sendResponse("Temperature (F): 37.5");
    	verify(rm,times(1)).sendResponse("Humidity (%): 30");
    	verify(rm,times(1)).sendResponse("https://cdn.weatherapi.com/weather/64x64/day/122.png");
    	
    
    }
    
    @Test
    void equals() {
    	//Given
        String city = "Bob";
        
        CurrentWeather cww = new CurrentWeather();
        Current c = new Current(); c.setTempF(37.5); c.setHumidity(30); Condition cond = new Condition(); cond.setIcon("//cdn.weatherapi.com/weather/64x64/day/122.png");c.setCondition(cond); cww.setCurrent(c);
        Location l = new Location(); l.setCountry("Boblandia"); cww.setLocation(l);
        
        //When
        when(wc.get()).thenReturn(requestHeadersUriSpecMock);
    	when(requestHeadersUriSpecMock.uri((Function<UriBuilder, URI>) any())).thenReturn(requestHeadersSpecMock);
    	when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
    	when(responseSpecMock.bodyToMono(CurrentWeather.class)).thenReturn(m_cw);

    	when(m_cw.block()).thenReturn(cww);
  
    	when(urib.queryParam("q", city)).thenReturn(urib);
    	when(urib.queryParam("key","7a59fc69444d4617a5a225318250309")).thenReturn(urib);
    	when(urib.build()).thenReturn(null);
    	
    	when(rm.getMessageContent()).thenReturn("weather"); // change this
    	
    	test.handle(rm);
    	
        //Then
        
    	verify(rm,times(1)).sendResponse("Please add a city.");
    }
    
    @Test
    
    void nothing() {
    	String city = "Bob";
        
        CurrentWeather cww = new CurrentWeather();
        Current c = new Current(); c.setTempF(37.5); c.setHumidity(30); Condition cond = new Condition(); cond.setIcon("//cdn.weatherapi.com/weather/64x64/day/122.png");c.setCondition(cond); cww.setCurrent(c);
        Location l = new Location(); l.setCountry("Boblandia"); cww.setLocation(l);
        
        //When
        when(wc.get()).thenReturn(requestHeadersUriSpecMock);
    	when(requestHeadersUriSpecMock.uri((Function<UriBuilder, URI>) any())).thenReturn(requestHeadersSpecMock);
    	when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
    	when(responseSpecMock.bodyToMono(CurrentWeather.class)).thenReturn(m_cw);

    	when(m_cw.block()).thenReturn(cww);
  
    	when(rm.getMessageContent()).thenReturn("banana"); // change this
    	
    	test.handle(rm);
    	
        //Then
        
    	verify(rm,never()).sendResponse(anyString());
    }
}
