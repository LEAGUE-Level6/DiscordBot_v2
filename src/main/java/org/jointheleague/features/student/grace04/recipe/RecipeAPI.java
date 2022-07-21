package org.jointheleague.features.student.grace04.recipe;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class RecipeAPI extends Feature {
    public final String COMMAND = "!recipe";

    private WebClient webClient;
    private static final String baseUrl = "https://api.spoonacular.com/recipes/complexSearch";

    private String apiKey = "0044c0e3c7ec461fa85dd894f7a760d4";

    public RecipeAPI(String channelName) {
        super(channelName);

        helpEmbed = new HelpEmbed(COMMAND, "Find a recipe");

        //build the WebClient
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void handle(MessageCreateEvent event){

        String messageContent = event.getMessageContent();
        String[] messageArr = messageContent.split(" ");
        if (messageContent.equals(COMMAND)) {
            event.getChannel().sendMessage("Use the command '!recipe [dish]' to find a recipe");
        }
        if(messageArr[0].equals(COMMAND) && messageArr.length > 1 && messageArr[1]!= null){
            String toPrint = findRecipe(messageArr[1]);
            event.getChannel().sendMessage(toPrint);
        }
    }

    public RecipeWrapper getRecipeFromTopic(String topic) {
        Mono<RecipeWrapper> recipeWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apiKey", apiKey)
                        .queryParam("query", topic)
                        .queryParam("sort", "popularity")
                        .build())
                .retrieve()
                .bodyToMono(RecipeWrapper.class);

        return recipeWrapperMono.block();
    }

    public String findRecipe(String topic){

        //Get a story from News API
        RecipeWrapper rw = getRecipeFromTopic(topic);

        //get all possible recipes from initial search
        Recipe[] recipes = rw.getResults();

        //Get the first recipe
        Recipe newRecipe = recipes[0];
        //results[0];

        //Get the title of the recipe
        String recipeTitle = newRecipe.getTitle();

        //get image
        String recipeImage = newRecipe.getImage();

        //get id
        int recipeId = newRecipe.getId();

        // TEXT WItHIN LINK TO SEND
        String linkText = "";
        String[] sendArr = recipeTitle.split(" ");
        for(int i = 0; i < sendArr.length; i++){
            linkText = sendArr[i] + "-";
        }


        //Create the message
        String message =
                recipeTitle + "\n"
//                        + recipeCalories + "\n"
                        + recipeImage + "\n"
                        + "Link to recipe: https://spoonacular.com/" + linkText + recipeId;



        //"https://spoonacular.com/pasta-with-garlic-scallions-cauliflower-breadcrumbs-716429"



        //Send the message
        return message;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}
