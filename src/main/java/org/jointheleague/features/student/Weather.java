package org.jointheleague.features.student;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

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
            event.getChannel().sendMessage(location);
            event.getChannel().sendMessage("Testing...");
            String data = requestWeather(location);
            System.out.println(data);
            event.getChannel().sendMessage("Working");
        }
    }

    public String requestWeather(String location){
        Mono<String> stringMono = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("key", "1bbb25a126c4416abc8190539231903")
                        .queryParam("q", location)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
        System.out.println("hhhhhhhhhh");
        Optional<String> result = stringMono.blockOptional();
        if (result.equals(Optional.empty())){
            return "Error";
        }
        System.out.println("passed");
        String response = stringMono.block();
        System.out.println("hello");

        return response;
    }

}
