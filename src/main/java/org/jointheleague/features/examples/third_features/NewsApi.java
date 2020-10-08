package org.jointheleague.features.examples.third_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.ApiExampleWrapper;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Article;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//Documentation for the API can be found here: https://newsapi.org/docs/get-started
public class NewsApi extends Feature {

    public final String COMMAND = "!newsApi";

    private WebClient webClient;
    private static final String baseUrl = "http://newsapi.org/v2/everything";
    private final String apiKey = "59ac01326c584ac0a069a29798794bec";

    public NewsApi(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Example of using an API to get information from another service.  This returns a news story related to a topic (e.g. !apiExample cats)");

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
                    .replace(COMMAND + " ", "");
            if (messageContent.equals("")) {
                event.getChannel().sendMessage("Please put a topic after the command (e.g. " + COMMAND + " cats)");
            }
            else{
                String story = getNewsStoryStringByTopic(messageContent);
                event.getChannel().sendMessage(story);
            }
        }
    }

    public String getNewsStoryStringByTopic(String topic) {
        Mono<ApiExampleWrapper> apiExampleWrapperFlux = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", topic)
                        .queryParam("sortBy", "popularity")
                        .queryParam("apiKey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(ApiExampleWrapper.class);

        //collect the response into a java object using the plain old java objects you created
        ApiExampleWrapper apiExampleWrapper = apiExampleWrapperFlux.block();

        //get the first article (these are plain old java objects now)
        Article article = apiExampleWrapper.getArticles().get(0);

        //get the title of the article
        String articleTitle = article.getTitle();

        //get the content of the article
        String articleContent = article.getContent();

        //get the URL of the article
        String articleUrl = article.getUrl();

        //create the message
        String message =
                articleTitle + " -\n"
                        + articleContent
                        + "\nFull article: " + articleUrl;

        //return the message
        return message;
    }

}

