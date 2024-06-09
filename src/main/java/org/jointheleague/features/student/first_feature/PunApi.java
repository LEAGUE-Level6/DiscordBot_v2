package org.jointheleague.features.student.first_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.student.first_feature.PunWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class PunApi extends FeatureTemplate {

    public final String COMMAND = "!punApi";

    private WebClient webClient;
    private static final String baseUrl =  "https://www.punapi.rest/api/pun";

    public PunApi(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, " Uses an API to returns a random pun from the internet. NOT guaranteed to be funny.");

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

        Mono<PunWrapper> mono = webClient.get()
                .retrieve()
                .bodyToMono(PunWrapper.class);


        PunWrapper r = mono.block();
        return r.toString();
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}