package org.jointheleague.features.whale;

import org.jointheleague.features.whale.DataTrasnferObjects.ImageSearchResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class SerpAPI {
	private static final String baseUrl = "https://serpapi.com/search.json?engine=google&q=%searchhere%&location=United+States&google_domain=google.com&gl=us&hl=en&tbm=isch&api_key=5d839296455a3e7b9dcc3d060fb7ad2584f9dc2eb413c98089ce0a51d756bb25";

    private WebClient webClient;

    public SerpAPI() {
     
    }

    public void testRequest(String q){
        /*
        Use the WebClient to make the request, converting the response to String.class.
        This request doesn't require url parameters, so you can omit the .uri() method call entirely
        */
    	   this.webClient = WebClient
                   .builder()
                   .baseUrl(baseUrl.replace("%searchhere%", q))
                   .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                   .build();
    	Mono<ImageSearchResult> stringMono = webClient.get().retrieve().bodyToMono(ImageSearchResult.class);
        //Collect the response from the Mono object
    	ImageSearchResult response = stringMono.block();
    	String imageURL = response.getImagesResults().get(0).getOriginal();
    	System.out.println("url of 1st image " + imageURL);
        /*
        Print out the actual JSON response -
        this is what you will input into jsonschema2pojo.com
         */


        /*
        Use http://www.jsonschema2pojo.org/ to generate your POJO
        and place it in the cat_facts_API.data_transfer_objects package.
        Select:
        Class name: CatWrapper
        Target Language = java
        Source Type = JSON
        Annotation Style = Gson
        */
    }

    public SerpAPI getImage() {
    	Mono<SerpAPI> stringMono = webClient.get().retrieve().bodyToMono(SerpAPI.class);
        //Collect the response from the Mono object
    	SerpAPI response = stringMono.block();
        //Make the request, saving the response in an object of the type that you just created in your
        //data_transfer_objects package (CatWrapper)

        //Use block() to collect the response into a java object using the class you just created

        //return the Object
        return response;


    }

    public String findCatFact(){
        //use the getCatFact method to retrieve a cat fact
    	SerpAPI image = getImage();
        //return the first (and only) String in the Arraylist of data in the response
        return null;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
