package org.jointheleague.features.student.third_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//A swagger page for this very simple API can be found here: https://app.swaggerhub.com/apis-docs/whiterabbit8/meowfacts/1.0.0
public class Apod extends Feature {

    public final String COMMAND = "!Apod";

    private WebClient webClient;
    private static final String baseUrl = "https://api.nasa.gov/planetary/apod";
    String apiKey = "m31WHdVpBpQaaAqyF0oNJeKsJoGYVqHIYyJPP4IA";

    public Apod(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Shows the Astrology Picture of the day.");

        //build the WebClient
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            String picture = apod();
            event.getChannel().sendMessage(picture);

        }
    }


    public String apod() {

        Mono<String> apodWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apiKey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class);

        String message = apodWrapperMono.block();
System.out.println(message);
        //send the message
        return message;
    }
}