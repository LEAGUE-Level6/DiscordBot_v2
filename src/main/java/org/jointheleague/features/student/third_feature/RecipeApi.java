package org.jointheleague.features.student.third_feature;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


public class RecipeApi extends Feature {

	    public final String COMMAND = "!recipeApi";

	    private WebClient webClient;
	    private static final String baseUrl = "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";
	    private final String apiKey = "eaf0e5e978msh83da62360af2aa6p1af406jsn8f0cceea3bea";  
	    public RecipeApi(String channelName) {
	    	
	        super(channelName);
	        helpEmbed = new HelpEmbed(COMMAND, "Using an API to get recipe information.");

	        this.webClient = WebClient
	                .builder()
	                .baseUrl(baseUrl)
	                .defaultHeader("x-rapidapi-key", apiKey) 
	                .defaultHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com") 
	                .build();
	    }

	    @Override
	    public void handle(ReceivedMessage event) {
	    	System.out.println("Handling Message.");
	        String messageContent = event.getMessageContent();
	        if (messageContent.startsWith(COMMAND)) {
	            messageContent = messageContent
	                    .replace(COMMAND, "")
	                    .replace(" ", "");
	            System.out.println("Found Command.");
	            if (messageContent.equals("")) {
	            	System.out.println("Did not find prompt");
	                event.sendResponse("Please provide a recipe name after the command: " + COMMAND + " italian wedding soup)");
	            } else {
	            	System.out.println("Finding recipe.");
	                String recipeDetails = findRecipe(messageContent);
	                event.sendResponse(recipeDetails);
	            }
	        }
	    }

	    public RecipeWrapper getRecipe(String food) {
	    	System.out.println("getting recipe.");
	    	RecipeWrapper recipeWrapper = webClient.get()
	    		    .uri(uriBuilder -> uriBuilder
	    		    	.path("/recipes/complexSearch")
	    		        .queryParam("query", food)
	    		        .queryParam("number", 1)
	    		        .build())
	    		    .retrieve()
	    		    .bodyToMono(RecipeWrapper.class)
	    		    .block();

	    	System.out.println("Got recipe.");
	    	System.out.println("Recipe: " + recipeWrapper);
	        return recipeWrapper;
	    }

	    public String findRecipe(String food) {
	    	System.out.println("Finding recipe in findRecipe().");
	        RecipeWrapper recipeWrapper = getRecipe(food);
	        System.out.println("Found recipe");
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

	


