package org.jointheleague.features.student.third_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class AIStoryMaker extends Feature {

    public final String COMMAND = "!AIStoryMaker";
    private static final String API_URL = "https://api.capix.uz/ai-story-generator";
    private WebClient webClient;

    public AIStoryMaker(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "This makes a random AI generated Story. !AIStoryMaker [your prompt]");

        // Build the WebClient
        this.webClient = WebClient.builder().baseUrl(API_URL).build();
    }

    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            String prompt = messageContent.substring(COMMAND.length());
            if (prompt.isEmpty()) {
                event.sendResponse("Please provide a prompt");
                return;
            }

            fetchStoryFromAPI(prompt).subscribe(event::sendResponse);
        }
    }

    private Mono<String> fetchStoryFromAPI(String prompt) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("prompt", prompt).build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
