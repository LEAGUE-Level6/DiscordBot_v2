package org.jointheleague.features.Derek;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.examples.first_features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.ApiExampleWrapper;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Article;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Locale;

public class WeatherApi extends Feature {

    private WebClient webClient;
    public final String COMMAND = "!weatherApi";
    private static final String baseUrl = "http://api.weatherapi.com/v1/current.json";
    String apiKey = System.getenv("API_TOKEN");

    public WeatherApi(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Get the weather in any area! Realtime!"
        );
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        String[] message = messageContent.split(" ",2);
        if (messageContent.startsWith(COMMAND)&&message.length>=2) {
            event.getChannel().sendMessage(findWeather(message[1]));
        }
    }

    public WeatherData getWeatherByLocation(String location) {
        Mono<WeatherData> apiExampleWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("key", apiKey).queryParam("q", location).build())
                .retrieve()
                .bodyToMono(WeatherData.class);
        return apiExampleWrapperMono.block();
    }

    public String findWeather(String location){

        //Get a story from News API
        WeatherData weatherData = getWeatherByLocation(location);


        //Create the message
        String message = "The current time in "+location+", "+weatherData.location.region+", "+weatherData.location.country+" is "+weatherData.location.localtime+
                "\nThe current weather is "+weatherData.current.condition.text.toLowerCase(Locale.ROOT)+
                "\nThe current temperature is "+weatherData.current.temp_f+"℉"+
                "\nThe temperature feels like "+weatherData.current.feelslike_f+"℉"
                ;

        //Send the message.
        return message;
    }

}