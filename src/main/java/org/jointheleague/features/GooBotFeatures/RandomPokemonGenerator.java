package org.jointheleague.features.GooBotFeatures;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.cat_facts_api.CatWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//A swagger page for this very simple API can be found here: https://app.swaggerhub.com/apis-docs/whiterabbit8/meowfacts/1.0.0
public class RandomPokemonGenerator extends Feature {



    // FIND A API TO USE
    public final String COMMAND = "!RandomPokemonGenerator";

    private WebClient webClient;
    private static final String baseUrl = "foo";

    public RandomPokemonGenerator(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "This will grab a random pokemon of the 1025 that exist, and " +
                "give the name!");

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
            String catFact = getCatFact();
            event.getChannel().sendMessage(catFact);
        }
    }

    public String getCatFact() {

        //Make the request, accepting the response as a plain old java object you created
        Mono<CatWrapper> catWrapperMono = webClient.get()
                .retrieve()
                .bodyToMono(CatWrapper.class);

        //collect the response into a plain old java object
        CatWrapper catWrapper = catWrapperMono.block();

        //get the cat fact from the response
        String message = catWrapper.getData().get(0);

        //send the message
        return message;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}


