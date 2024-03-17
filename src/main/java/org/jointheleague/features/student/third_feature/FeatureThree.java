package org.jointheleague.features.student.third_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.cat_facts_api.CatWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class FeatureThree extends Feature {

    public final String COMMAND = "!catFactApi";

    private WebClient webClient;
    private static final String baseUrl = "https://meowfacts.herokuapp.com/";

    public FeatureThree(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Example of using an API to get information from another service.  This returns a cat fact");

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
            String catFact = getFact();
            event.getChannel().sendMessage(catFact);
        }
    }
    public String getFact() {

        //Make the request, accepting the response as a plain old java object you created
      

        //collect the response into a plain old java object

        //get the cat fact from the response

        //send the message
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
