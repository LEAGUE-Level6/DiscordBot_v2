package org.jointheleague.features.examples.third_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.AnimalWrapper;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.cat_facts_api.CatWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class AfricanAnimalFactApi extends Feature{


    //A swagger page for this very simple API can be found here: https://app.swaggerhub.com/apis-docs/whiterabbit8/meowfacts/1.0.0

        public final String COMMAND = "!africaFact";

        private WebClient webClient;
        private static final String baseUrl = "https://african-animal-facts.onrender.com/rand/";

        public AfricanAnimalFactApi(String channelName) {
            super(channelName);
            helpEmbed = new HelpEmbed(COMMAND, "Example of using an API to get information from another service.  This returns a african animal fact");

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
                String africaFact = getAnimalFact();
                event.getChannel().sendMessage(africaFact);
            }
        }

        public String getAnimalFact() {

            //Make the request, accepting the response as a plain old java object you created
            Mono<AnimalWrapper> animalWrapperMono = webClient.get()
                    .retrieve()
                    .bodyToMono(AnimalWrapper.class);

            //collect the response into a plain old java object
            AnimalWrapper animalWrapper = animalWrapperMono.block();
            System.out.println(animalWrapper.fact);
            //send the message
            return animalWrapper.fact;
        }

        public void setWebClient(WebClient webClient) {
            this.webClient = webClient;
        }

    }

