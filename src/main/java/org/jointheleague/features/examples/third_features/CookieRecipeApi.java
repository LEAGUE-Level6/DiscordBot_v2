package org.jointheleague.features.examples.third_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
                event.getChannel().sendMessage("Please put a flavor after the command (e.g. " + COMMAND + " sugar cookie)");
            }
            else{
                String recipe = findRecipe(messageContent);
                event.getChannel().sendMessage(recipe);
            }
        }
    }
    //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
//make into string and parse instead
    public RecipeList getRecipes(String query) {
        Mono<RecipeList> resultsMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", query)
                        .queryParam("apiKey", apiKey)
                        .queryParam("sort", "popularity")
                        .build())
                .retrieve()
                .bodyToMono(RecipeList.class);

        return resultsMono.block();
    }

    public String findRecipe(String topic){
       //String result = getRecipes(topic);
       RecipeList results = getRecipes(topic);
       Recipe[] resultsArray= results.getResultsList();
       System.out.println(resultsArray);
       try {
           System.out.println("This is" + resultsArray.length + " array length");
       }
       catch (Exception e){
         e.printStackTrace();

       }
        Recipe recipe= results.getResultsList()[0];


        int recipeId= recipe.getId();
        String title = recipe.getTitle();
        String image= recipe.getImage();
        String imageType= recipe.getImageType();
        String id= String.valueOf(recipeId);
        String message = id+ "-\n"+title+"- \n"+ image+ "-\n"+ imageType;
        return message;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}

