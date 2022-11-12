package org.jointheleague.features.examples.third_features;

import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.SmashWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class SmashAPI extends Feature {
    public final String COMMAND = "!smashAPI";
    private WebClient webClient;
    private static final String baseUrl = "https://www.smashbros.com/en_US/";
    public SmashAPI(String channelName){
        super(channelName);
        HelpEmbed smash = new HelpEmbed("", "");

        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void handle(MessageCreateEvent event) throws APIException {

    }
    public String getFact(){
        Mono<SmashWrapper> smashWrapperMono = webClient.get().retrieve().bodyToMono(SmashWrapper.class);
        SmashWrapper smash = smashWrapperMono.block();
        String message = smash.getData().get(0);
        return message;
    }
}
