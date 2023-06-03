package org.jointheleague.features.examples.third_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.ApiExampleWrapper;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Article;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//Documentation for the API can be found here: https://newsapi.org/docs/get-started
public class CookieRecipeApi extends Feature {

    public final String COMMAND = "!CookieRecipe";

    private WebClient webClient;
    private static final String baseUrl = "https://api.spoonacular.com/recipes/complexSearch";
    private final String apiKey = "5d5c15ae4e624f0188135c1052a76bc7";

    public CookieRecipeApi(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "AN EXTENSIVE DIVE INTO COOKIES");

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
                event.getChannel().sendMessage("Please put a flavor after the command (e.g. " + COMMAND + " sugar)");
            }
            else{
                String recipe = findRecipe(messageContent);
                event.getChannel().sendMessage(recipe+" WORKING");
            }
        }
    }

    public Results getRecipes(String query) {
        Mono<Results> resultsMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", query)
                        .queryParam("apiKey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(Results.class);

        return resultsMono.block();
    }

    public String findRecipe(String topic){
       Results results = getRecipes(topic);

        int recipeId= results.getId();
        String title = results.getTitle();
        String image= results.getImage();
        String imageType= results.getImageType();
        String id= String.valueOf(recipeId);
        String message = id+ "-\n"+title+"- \n"+ image+ "-\n"+ imageType;
        return message;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}

