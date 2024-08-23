package org.jointheleague.features.whale;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class PexelsAPI {
	private static final String baseUrl = "https://api.pexels.com/v1/search?query=";

    private WebClient webClient;

    public PexelsAPI() {
     
    }

    public void testRequest(String q){
        /*
        Use the WebClient to make the request, converting the response to String.class.
        This request doesn't require url parameters, so you can omit the .uri() method call entirely
        */
    	   this.webClient = WebClient
                   .builder()
                   .baseUrl(baseUrl+q)
                   .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                   .defaultHeader("Authorization", "XVPfXCbnpVNRoqAXGE7ACGgD1qqtx3JWm0L1PoOrSPDcXEPVP5f132kv")
                   .build();
    	Mono<String> stringMono = webClient.get().retrieve().bodyToMono(String.class);
        //Collect the response from the Mono object
    	String response = stringMono.block();
    	System.out.println(response);
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

    public PexelsAPI getImage() {
    	Mono<PexelsAPI> stringMono = webClient.get().retrieve().bodyToMono(PexelsAPI.class);
        //Collect the response from the Mono object
    	PexelsAPI response = stringMono.block();
        //Make the request, saving the response in an object of the type that you just created in your
        //data_transfer_objects package (CatWrapper)

        //Use block() to collect the response into a java object using the class you just created

        //return the Object
        return response;


    }

    public String findCatFact(){
        //use the getCatFact method to retrieve a cat fact
    	PexelsAPI image = getImage();
        //return the first (and only) String in the Arraylist of data in the response
        return null;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
