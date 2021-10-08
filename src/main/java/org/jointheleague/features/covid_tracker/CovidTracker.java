package org.jointheleague.features.covid_tracker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class CovidTracker extends Feature {

    public final String COMMAND = "!covid";
    public final String apiKey = "ff20a04873f04e5097c7db61507ae45e";
    private WebClient webClient;
    private static final String baseUrl = "https://api.covidactnow.org/v2/";
    public CovidTracker(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "This is a game where you are provided with a scrambled word and you need to figure out what it is \n"
                + "To start the game do !gram (easy/meduim/hard). The different dificulties determin the word length \n"
                + "Once you begin to play, the next message you send will be considered your guess! Good luck!"
        );
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }
 //   ff20a04873f04e5097c7db61507ae45e
   //https://api.covidactnow.org/v2/country/US.json?apiKey={apiKey}    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if(messageContent.startsWith(COMMAND)) {
        	event.getChannel().sendMessage(test("US")+"");
        }
        //https://api.covidactnow.org/v2/country/US.json?apiKey={apiKey}
    }
	private Integer test(String countryString) {
		Mono<Cases> e = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("country/"+countryString+".json").queryParam("apiKey="+apiKey)
				.build())
                .retrieve()
                .bodyToMono(Cases.class);
return e.block().getAnomalies().get(0).getOriginalObservation();
	}

	

}

