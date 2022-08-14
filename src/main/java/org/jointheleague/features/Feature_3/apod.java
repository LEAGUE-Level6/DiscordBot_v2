package org.jointheleague.features.Feature_3;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.apod_wrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class apod extends Feature {

    public final String COMMAND = "!apod";

    private String baseURL = "https://api.nasa.gov/planetary/apod";
    private String key = "e1lM6Bw3Fkx4vhDjBmg5GlXhB5uclDnRwZJfiG8f";
    private WebClient client;
    private String explanation;
    public apod(String channelName) {
            super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "As a Gonk Droid, I enjoy space, so I give you image from Nasa's Astronomy Picture of the Day. Simply put in a date in the YYYY-MM-DD format or else you will get hit by a falling gonk droid . . .");

        this.client = WebClient.builder().baseUrl(baseURL).build();

    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            event.getChannel().sendMessage(apod());
            event.getChannel().sendMessage(explanation);
        }
    }

    public String apod(){
        Mono<apod_wrapper> apodMono = client.get()
                .uri(uriBuilder -> uriBuilder.queryParam("api_key", key).build())
                .retrieve()
                .bodyToMono(apod_wrapper.class);

        String output = apodMono.block().getURL();
        explanation = apodMono.block().getExplanation();
        return output;
    }
}
