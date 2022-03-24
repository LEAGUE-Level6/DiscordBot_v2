package org.jointheleague.features.student.grace04;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
//import org.jointheleague.features.student.grace04.tetr_api.ApiWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

public class HearthstoneAPI extends Feature {

    public final String COMMAND = "!hsAPI";

    private WebClient webClient;
    private static final String baseUrl = "https://rapidapi.com/omgvamp/api/hearthstone/";

    public HearthstoneAPI(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "This returns Hearthstone card info"
        );

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
                    .replace(" " , "");
            if (messageContent.equals("")) {
                event.getChannel().sendMessage("Please put a card name after the command");
            }
            else{
                String name = getCard(messageContent);
                event.getChannel().sendMessage(name);
            }
        }
    }

    public HearthstoneWrapper getCardByCard(String card) {
        Mono<String> hsWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("uri", "https://omgvamp-hearthstone-v1.p.rapidapi.com/cards/%7Bname%7D")
                        .queryParam("X-RapidAPI-Host", "omgvamp-hearthstone-v1.p.rapidapi.com")
                        .queryParam("X-RapidAPI-Key", "6fa354383amsh1ff11039b032073p1ed12cjsn5a0682d5c183")
                        .build())
                .retrieve()
                .bodyToMono(String.class);

        String result = hsWrapperMono.block();
        System.out.println(result);
        return null;
    }

    public String getCard(String card){

        HearthstoneWrapper hsWrapper = getCardByCard(card);

        /*String name = hsWrapper.getName();

        //Get the title of the article
        String articleTitle = article.getTitle();

        //Get the content of the article
        String articleContent = article.getContent();

        //Get the URL of the article
        String articleUrl = article.getUrl();

        //Create the message
        String message =
                articleTitle + " -\n"
                        + articleContent
                        + "\nFull article: " + articleUrl;

        //Send the message
        return message;*/
        return null;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
