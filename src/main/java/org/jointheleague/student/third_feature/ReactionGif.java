package org.jointheleague.student.third_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.student.third_feature.Gif;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Article;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ReactionGif extends Feature {

    public final String COMMAND = "!reaction";

    private WebClient webClient;
    private static final String baseUrl = "https://g.tenor.com/v1/search?";
    private final String apiKey = "YFQGYVBBKSWG";

    public ReactionGif(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Use this to get the perfect reaction to any message. e.g. " + COMMAND + " sad");

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
            messageContent = messageContent
                    .replace(COMMAND, "")
                    .replace(" ", "");
            if (messageContent.equals("")) {
                event.getChannel().sendMessage("Please put a reacion after the command e.g. " + COMMAND + " sad");
            } else {
                System.out.println("Send");
                System.out.println(findGif(messageContent));
                event.getChannel().sendMessage(findGif(messageContent));
            }
        }
    }

    public GifMaster getGifByTopic(String topic) {
        Mono<GifMaster> GifMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", topic)
                        .queryParam("limit", 1)
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(GifMaster.class);
        return GifMono.block();
    }

    public String findGif(String topic) {
        String message = getGifByTopic(topic).getResults().get(0).getUrl();
        return message;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}
