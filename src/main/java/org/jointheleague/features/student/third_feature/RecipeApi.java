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
	                String recipeDetails = null;
					try {
						recipeDetails = findRecipe(messageContent);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                event.sendResponse(recipeDetails);
	            }
	        }
	    }
	    public String newGetRecipe (String query, int number, boolean addRecipeInstructions) throws IOException, InterruptedException {
	    	StringBuilder uriBuilder = new StringBuilder("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/complexSearch");

	        // Append query parameters
	        uriBuilder.append("?query=").append(query);
	        uriBuilder.append("&number=").append(number);
	        uriBuilder.append("&addRecipeInformation=").append(addRecipeInstructions);
	        
	        HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(uriBuilder.toString()))
	            .header("x-rapidapi-key", apiKey)
	            .header("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
	            .GET()
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
	    
	   
	    
	    public String findRecipe(String food) throws IOException, InterruptedException {
	    	String responseJson = newGetRecipe(food, 1, false);  // Just get ID and title
	        RecipeWrapper recipeWrapper = new Gson().fromJson(responseJson, RecipeWrapper.class);
	        List<Recipe> recipes = recipeWrapper.getRecipes();

	        if (recipes == null || recipes.size() == 0) {
	            return "No recipes found.";
	        }

	        Recipe selectedRecipe = recipes.get(0);
	        int recipeId = selectedRecipe.getId();

	        String url = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/" + recipeId + "/information";

	        HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(url))
	            .header("x-rapidapi-key", apiKey)
	            .header("x-rapidapi-host", baseUrl)
	            .GET()
	            .build();

	        HttpResponse<String> fullInfoResponse = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

	        Recipe detailedRecipe = new Gson().fromJson(fullInfoResponse.body(), Recipe.class);

	        StringBuilder result = new StringBuilder();
	        result.append("**").append(detailedRecipe.getTitle()).append("**\n\n");

	        result.append("**Ingredients:**\n");
	        for (Ingredient ing : detailedRecipe.getIngredients()) {
	            result.append("- ").append(ing.getOriginal()).append("\n");
	        }

	        result.append("\n**Instructions:**\n");
	        result.append(detailedRecipe.getInstructions());
	        
	        System.out.println("Got instructions");
	        String resultString = result.toString();
	        resultString = resultString.replace("<.{1,10}>", "");
	        System.out.println(resultString);		
	        
	        return resultString;
	    }
	    	
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
	    
	    
	    

	    public void setWebClient(WebClient webClient) {
	        this.webClient = webClient;
	    }
	}

	


