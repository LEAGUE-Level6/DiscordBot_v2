package org.jointheleague.features.myfeatures.apifeature;

import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Article;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class RecipeAPI extends Feature {
    public final String COMMAND = "!food";

    private WebClient webClient;
    private static final String baseUrl = "https://api.spoonacular.com/recipes/complexSearch";

    public RecipeAPI(String channelName) {
        super(channelName);

        helpEmbed = new HelpEmbed(COMMAND, "Find a recipe :)");

        //build the WebClient
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void handle(MessageCreateEvent event){
        String messageContent = event.getMessageContent();
        if (messageContent.equals("!food")) {
            event.getChannel().sendMessage("Use the command '!food dish' to find a new recipe");
        }
    }

    public RecipeWrapper getRecipeFromTopic(String topic) {
        Mono<RecipeWrapper> recipeWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", topic)
                        .queryParam("sort", "popularity")
                        .build())
                .retrieve()
                .bodyToMono(RecipeWrapper.class);

        return recipeWrapperMono.block();
    }

    public String findStory(String topic){

        //Get a story from News API
  RecipeWrapper rw = getRecipeFromTopic(topic);

        //get all possible recipes from initial search
        Recipe[] recipes = rw.getResults();

  //Get the first recipe
        Recipe newRecipe = recipes[0];
                //results[0];

        //Get the title of the recipe
        String recipeTitle = newRecipe.getTitles();

        //get image
        String recipeImage = newRecipe.getImage();

        //Create the message
        String message =
                recipeTitle + " -\n"
                        + recipeImage;



        //Send the message
        return message;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}
