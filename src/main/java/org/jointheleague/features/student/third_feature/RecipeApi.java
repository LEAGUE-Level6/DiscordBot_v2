package org.jointheleague.features.student.third_feature;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


public class RecipeApi extends Feature {

	    public final String COMMAND = "!recipeApi";

	    private WebClient webClient;
	    private static final String baseUrl = "https://recipe-by-api-ninjas.p.rapidapi.com/v1/recipe";
	    private final String apiKey = "eaf0e5e978msh83da62360af2aa6p1af406jsn8f0cceea3bea";  
	    public RecipeApi(String channelName) {
	    	
	        super(channelName);
	        helpEmbed = new HelpEmbed(COMMAND, "Using an API to get recipe information.");

	        this.webClient = WebClient
	                .builder()
	                .baseUrl(baseUrl)
	                .build();
	    }

	    @Override
	    public void handle(ReceivedMessage event) {
	        String messageContent = event.getMessageContent();
	        if (messageContent.startsWith(COMMAND)) {
	            messageContent = messageContent
	                    .replace(COMMAND, "")
	                    .replace(" ", "");
	            if (messageContent.equals("")) {
	                event.sendResponse("Please provide a recipe name after the command: " + COMMAND + " italian wedding soup)");
	            } else {
	                String recipeDetails = findRecipe(messageContent);
	                event.sendResponse(recipeDetails);
	            }
	        }
	    }

	    public RecipeWrapper getRecipe(String food) {
	        Mono<RecipeWrapper> recipeWrapperMono = webClient.get()
	                .uri(uriBuilder -> uriBuilder
	                        .queryParam("query", food)
	                        .queryParam("apiKey", apiKey)
	                        .build())
	                .retrieve()
	                .bodyToMono(RecipeWrapper.class);

	        return recipeWrapperMono.block();
	    }

	    public String findRecipe(String food) {
	        RecipeWrapper recipeWrapper = getRecipe(food);

	        if (recipeWrapper != null ) {
	            Recipe recipe = recipeWrapper.getRecipes().get(0);

	            String title = recipe.getTitle();
	            String ingredients = String.join(",", recipe.getIngredients());
	            String instructions = recipe.getInstructions();

	            return String.format("Recipe: "+title+"\nIngredients: " +ingredients + "\nInstructions: "+instructions);
	        } else {
	            return "No recipe found";
	        }
	    }

	    public void setWebClient(WebClient webClient) {
	        this.webClient = webClient;
	    }
	}

	


