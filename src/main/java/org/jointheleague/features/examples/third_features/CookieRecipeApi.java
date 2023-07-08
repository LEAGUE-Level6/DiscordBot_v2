package org.jointheleague.features.examples.third_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class CookieRecipeApi extends Feature {

    public final String COMMAND = "!Recipe";

    private WebClient webClient;
    private static final String baseUrl = "https://api.spoonacular.com/recipes/complexSearch";
    private final String apiKey = "5d5c15ae4e624f0188135c1052a76bc7";

    public CookieRecipeApi(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "I have failed the Cookie, Use this to get recipes... not always a cookie");

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
                event.getChannel().sendMessage("Please put a recipe after the command (e.g. " + COMMAND + " sugar cookie)");
            }
            else{
                if(messageContent.contains("Cookie")||messageContent.contains("cookie")){
                    String recipe = findRecipe(messageContent);
                    event.getChannel().sendMessage(recipe);
                }
                else {
                    event.getChannel().sendMessage("No Cookie?? \n https://media.pocketgamer.com/artwork/na-35277-1680078860/cookie-clicker-grandmapocalypse-artwork-1.jpg");
                    String recipe = findRecipe(messageContent);
                    event.getChannel().sendMessage(recipe);
                }
            }
        }
    }

//make into string and parse instead
    public RecipeList getRecipes(String recipe) {
        Mono<RecipeList> resultsMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", recipe)
                        .queryParam("apiKey", apiKey)
                        .queryParam("titleMatch","Cookie")
                        .queryParam("sort", "popularity")
                        .queryParam("includeIngredients","Sugar","Flour" )
                        .build())
                .retrieve()
                .bodyToMono(RecipeList.class);

        return resultsMono.block();
    }

    public String findRecipe(String topic){

       RecipeList results = getRecipes(topic);
       Recipe[] resultsArray= results.getResults();

       try {
           System.out.println("This is" + resultsArray.length + " array length");
       }
       catch (Exception e){
         e.printStackTrace();

       }

        Recipe recipe= results.getResults()[0];


        int recipeId= recipe.getId();
        String title = recipe.getTitle();
        String image= recipe.getImage();
      //  String imageType= recipe.getImageType();
        String id= String.valueOf(recipeId);


        String link = "https://spoonacular.com/";
        String[] sendArr = title.split(" ");
        int length= sendArr.length;
        String stringId= Integer.toString(recipeId);
        link=link+=sendArr[length-1]+"-"+stringId;

        String message =title+"- \n"+ image+"\n "+link;
        return message;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}

