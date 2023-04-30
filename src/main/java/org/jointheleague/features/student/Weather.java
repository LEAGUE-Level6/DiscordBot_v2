package org.jointheleague.features.student;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.student.weatherwrapper.WeatherWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class Weather extends Feature {

    public final String COMMAND = "!weather";

    private static final String baseUrl = "https://api.weatherapi.com/v1/current.json";

    private WebClient webClient;

    public Weather(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "A weather program"
        );

        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            String location = messageContent.substring(8).trim();
            event.getChannel().sendMessage("Testing...");
            WeatherWrapper data = requestWeather(location);

            String message = "";
            if (data == null){
                message = "Illegal location.\nPlease try again.";
            }else {
                message = "The weather in " + data.getLocation().getName() + " " + data.getLocation().getRegion()
                + ", " + data.getLocation().getCountry() + " as of " + data.getCurrent().getLastUpdated() + ":" +
                "\n" + data.getCurrent().getTempF() + " degrees F\n" + data.getCurrent().getWindMph() + " MPH Wind\n"
                + data.getCurrent().getCondition().getText();
            }
            event.getChannel().sendMessage(message);

        }
    }

    public WeatherWrapper requestWeather(String location){
        Mono<WeatherWrapper> stringMono = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("key", "1bbb25a126c4416abc8190539231903")
                        .queryParam("q", location)
                        .build())
                .retrieve()
                .bodyToMono(WeatherWrapper.class);
        try {
            WeatherWrapper result = stringMono.block(Duration.of(2000, ChronoUnit.MILLIS));
        }catch(Exception e){
            return null;
        }
        System.out.println("passed");
        WeatherWrapper response = stringMono.block();
        System.out.println(stringMono.toString());

        return response;
    }

}
