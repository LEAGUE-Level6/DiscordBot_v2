package org.jointheleague.features.student.third_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.student.third_features.plain_old_java_objects.bored_api.FeatureThreeWrapper;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class FeatureThree extends Feature {

    public final String COMMAND = "!bored";

    private WebClient webClient;
    private static final String baseUrl = "https://www.boredapi.com/api/activity";

    public FeatureThree(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "This will return an activity you can do when you're bored.");

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
            String activity = getActivity();
            event.getChannel().sendMessage(activity);
        }
    }
    public String getActivity() {

        //Make the request, accepting the response as a plain old java object you created
    	Mono<FeatureThreeWrapper> wrapperMono = webClient.get().retrieve().bodyToMono(FeatureThreeWrapper.class);

        //collect the response into a plain old java object
    	FeatureThreeWrapper wrapper = wrapperMono.block();
        //get the cat fact from the response
    	String message = wrapper.getActivity();
        //send the message
        System.out.println(message);
    	return message;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
