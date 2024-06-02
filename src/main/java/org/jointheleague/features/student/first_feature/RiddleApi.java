package org.jointheleague.features.student.first_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.student.first_feature.RiddleWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class RiddleApi extends FeatureTemplate {

    public final String COMMAND = "!riddleApi";

    private WebClient webClient;
    private static final String baseUrl =  "https://riddles-api.vercel.app/random";

    public RiddleApi(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Example of using an API to get information from another service. This returns a riddle.");

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
            String riddle = getRiddle();
            event.getChannel().sendMessage(riddle);
        }
    }

    public String getRiddle() {

        Mono<RiddleWrapper> mono = webClient.get()
                .retrieve()
                .bodyToMono(RiddleWrapper.class);


        RiddleWrapper r = mono.block();
        return r.toString();
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}

