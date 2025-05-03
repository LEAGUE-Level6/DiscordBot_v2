package org.jointheleague.features.student.third_feature;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;

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
	                Recipe recipeDetails = findRecipe(messageContent);
	                event.sendResponse(recipeDetails.getRecipe());
	            }
	        }
	    }
	    public String newGetRecipe () throws IOException, InterruptedException {
	    	HttpRequest request = HttpRequest.newBuilder()
	    			.uri(URI.create("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/complexSearch"))
	    			.header("x-rapidapi-key", apiKey)
	    			.header("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
	    			.method("GET", HttpRequest.BodyPublishers.noBody())
	    			.build();
	    	HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
	    	return response.body();
	    }
	    
	    public String getRecipe(String food) {
	    	System.out.println("getting recipe.");
	    	Mono<String> recipeWrapper = webClient.get()
	    		    .uri(uriBuilder -> uriBuilder
	    		        .queryParam("query", food)
	    		        .queryParam("apiKey", apiKey)
	    		        .build())
	    
	    
	    		    	
	    			.retrieve()
	    		    .bodyToMono(String.class);
	    			
	    			System.out.println("Mono to string: " + recipeWrapper);
	    			String blocked = recipeWrapper.block();
	    			
	    		System.out.println(blocked);
	    	 //if (recipeWrapper != null && recipeWrapper.getRecipes().size() > 0) {
	    		 	
	    	        //return recipeWrapper.getRecipes().get(0);
	    	    //}
	    	    return blocked;
	    }
	    
	    public Recipe getRecipeDetail(int id) {
	        return webClient.get()
	            .uri("/recipes/" + id + "/information")
	            .retrieve()
	            .bodyToMono(Recipe.class) 
	            .block();
	    }
	    
	    public Recipe findRecipe(String food) {
	    	Recipe data;
//	    	try {
				String testRecipe = getRecipe(food);
				System.out.println(testRecipe);
				RecipeWrapper recipeWrapper = new Gson().fromJson(testRecipe, RecipeWrapper.class);
				System.out.println("RecipeWrapped"+ recipeWrapper);
				List<Recipe> recipes = recipeWrapper.getRecipes();
				System.out.println("Got List "+recipes.size());
				data = recipes.get(0);
				System.out.println("data.getTitle(): "+data.getTitle());
				return data;
//			} 
//	    	catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	    	return null;
	    	
	    	
//	    	Recipe recipeSummary = getRecipe(food);
//	        if (recipeSummary != null) {
//	            Recipe fullRecipe = getRecipeDetail(recipeSummary.getId());
//
//	            if (fullRecipe != null) {
//	                String title = fullRecipe.getTitle();
//	                String instructions = fullRecipe.getInstructions();
//	                List<Ingredient> ingredients = fullRecipe.getIngredients();
//
//	                return String.format(title + ": \n" + "Ingredients: "+ ingredients +"\n Instructions: "+ instructions);
//	            }
//	        }
//	        return "No recipe found.";
	    }
	    
	    

	    public void setWebClient(WebClient webClient) {
	        this.webClient = webClient;
	    }
	}

	


