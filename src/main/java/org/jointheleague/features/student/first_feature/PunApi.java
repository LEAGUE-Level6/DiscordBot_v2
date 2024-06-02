package org.jointheleague.features.student.first_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.student.first_feature.PunWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.Arrays;

public class PunApi {


    private WebClient webClient;
    private static final String baseUrl = "https://www.punapi.rest/api/pun";

    public PunApi(){

        //build the WebClient
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    public String getPun() {

        //Make the request, accepting the response as a plain old java object you created
        Mono<PunWrapper> punWrapperMono = webClient
                .get()
                .retrieve()
                .bodyToMono(PunWrapper.class);
        System.out.println("Uno");
        //collect the response into a plain old java object
        PunWrapper punWrapper = punWrapperMono.block();
        System.out.println("Dos");
        //get the pun from the response
        String message = punWrapper.getData().get(0);
        System.out.println("Tres");
        //send the message
        return message;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}


