package org.jointheleague.features.examples.third_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.cat_facts_api.RiddleWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;

import net.aksingh.owmjapis.api.APIException;
import reactor.core.publisher.Mono;

public class RiddleApi extends Feature{

	 public final String COMMAND = "!riddle";

	    private WebClient webClient;
	    private static final String baseUrl = "https://api.fungenerators.com";

	public RiddleApi(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Example of using an API to get information from another service.  This returns a cat fact");

        //build the WebClient
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    
	}

	@Override
	public void handle(MessageCreateEvent event){
		// TODO Auto-generated method stub
		 String messageContent = event.getMessageContent();
	        if (messageContent.startsWith(COMMAND)) {
	            String riddle = getRiddle();
	            event.getChannel().sendMessage(riddle);
	        }
	}
	
	 public String getRiddle() {

	        //Make the request, accepting the response as a plain old java object you created
	        Mono<RiddleWrapper> riddleMono = webClient.get()
	                .retrieve()
	                .bodyToMono(RiddleWrapper.class);

	        //collect the response into a plain old java object
	        RiddleWrapper r = riddleMono.block();

	        //get the cat fact from the response
	        String message = r.getData().get(0);

	        //send the message
	        return message;
	    }
	 
	 public void setWebClient(WebClient webClient) {
	        this.webClient = webClient;
	    }

	

}
