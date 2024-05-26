package org.jointheleague.features.student.first_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.student.first_feature.PunWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class PunApi extends Feature {

    public final String COMMAND = "!punApi";

    private WebClient webClient;
    private static final String baseUrl = "https://punapi.rest/api/pun";

    public PunApi(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Returns a random pun from a public API");

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
            String pun = getPun();
            event.getChannel().sendMessage(pun);
        }
    }

    public String getPun() {

        //Make the request, accepting the response as a plain old java object you created
        Mono<PunWrapper> punWrapperMono = webClient.get()
                .retrieve()
                .bodyToMono(PunWrapper.class);

        //collect the response into a plain old java object
        PunWrapper punWrapper = punWrapperMono.block();

        //get the cat fact from the response
        String message = punWrapper.getData().get(0);

        //send the message
        return message;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}


