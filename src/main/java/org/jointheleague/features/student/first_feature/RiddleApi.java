package org.jointheleague.features.student.first_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.ApiExampleWrapper;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Article;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class RiddleApi extends FeatureTemplate {

    public final String COMMAND = "!riddleApi";

    private WebClient webClient;
    private static final String baseUrl = "https://riddles-api.vercel.app/random";
    private final String apiKey = "59ac01326c584ac0a069a29798794bec";

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
            messageContent = messageContent
                    .replace(COMMAND, "")
                    .replace(" " , "");
            if (messageContent.equals("")) {
                event.getChannel().sendMessage("Please put a topic after the command (e.g. " + COMMAND + " topic)");
            }
            else{
                String story = findStory(messageContent);
                event.getChannel().sendMessage(story);
            }
        }
    }

    public ApiExampleWrapper getNewsStoryByTopic(String topic) {
        Mono<ApiExampleWrapper> apiExampleWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", topic)
                        .queryParam("sortBy", "popularity")
                        .queryParam("apiKey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(ApiExampleWrapper.class);

        return apiExampleWrapperMono.block();
    }

    public String findStory(String topic){

        //Get a story from News API
        ApiExampleWrapper apiExampleWrapper = getNewsStoryByTopic(topic);

        //Get the first article
        Article article = apiExampleWrapper.getArticles().get(0);

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
        return message;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}

