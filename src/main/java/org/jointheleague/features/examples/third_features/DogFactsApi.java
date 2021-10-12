package org.jointheleague.features.examples.third_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.dog_facts_api.DogFactsApiWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class DogFactsApi extends Feature {

    public final String COMMAND = "!dogFacts";

    private WebClient webClient;
    private static final String baseUrl = "https://dog-facts-api.herokuapp.com/api/v1/resources/dogs?number=";
//    https://www.jsonschema2pojo.org/

    public DogFactsApi(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Gets facts about dogs (usage: !dogFacts, !dogFacts numOfFacts)");

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
        	String otherWord = messageContent.replaceAll(" ", "").replace(COMMAND, "");
        	int numberOfFacts;
        	try {
        		numberOfFacts = Integer.parseInt(otherWord);
        	}
        	catch (NumberFormatException e) {
        		numberOfFacts = 1;
        	}
        	this.webClient = WebClient
                    .builder()
                    .baseUrl(baseUrl + numberOfFacts)
                    .build();
            String dogFact = getDogFact();
            event.getChannel().sendMessage(dogFact);
        }
    }

    public String getDogFact() {

        //Make the request, accepting the response as a plain old java object you created
    	System.out.println("control-f for 'abcdefg' Trying to get dog facts");
        Mono<DogFactsApiWrapper[]> dfMono = webClient.get()
                .retrieve()
                .bodyToMono(DogFactsApiWrapper[].class);

        //collect the response into a plain old java object
        DogFactsApiWrapper[] dfw = dfMono.block();

        //get the cat fact from the response
        String message = "";
        for (int i = 0; i < dfw.length; i++) {
        	message = message + dfw[i].getFact() + "\n";
        }

        //send the message
        return message;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}


