package org.jointheleague.features.student.third_features;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.student.third_features.plain_old_java_objects.ApodApi.ApodWrapper;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.xml.ws.handler.MessageContext;

//A swagger page for this very simple API can be found here: https://app.swaggerhub.com/apis-docs/whiterabbit8/meowfacts/1.0.0
public class Apod extends Feature {


    public final String COMMAND = "!Apod";
    private WebClient webClient;
    private static final String baseUrl = "https://api.nasa.gov/planetary/apod";
    String apiKey = "m31WHdVpBpQaaAqyF0oNJeKsJoGYVqHIYyJPP4IA";
    public Apod(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Shows the Astrology Picture of the day. If a date is put after !Apod, you will get the Picture for that date. Enter date like YYYY-MM-DD.");

        //build the WebClient
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        System.out.println(messageContent);
        if (messageContent.startsWith(COMMAND)) {
     String[] stringSplit = messageContent.split(" ");

            System.out.println(stringSplit.length);
            if (stringSplit.length == 1){
                String picture = apod();
                event.getChannel().sendMessage(picture);
            }else{
                String[] stringSplit2 = stringSplit[1].split("-");
                if (stringSplit2[0].length() != 4) {
                    event.getChannel().sendMessage("Enter either !Apod, or !Apod <date>. Enter Date like YYYY-MM-DD");
                }
                for (int i = 1; i< stringSplit2.length; i++){
                    if (stringSplit2[i].length() != 2 && stringSplit2[i].length() != 1) {
                            event.getChannel().sendMessage("Enter either !Apod, or !Apod <date>. Enter Date like YYYY-MM-DD");
                            break;
                        }
                }
                String date = stringSplit[1];
                if (date.contains("-")){
                    String picture = apod(date);
                    event.getChannel().sendMessage(picture);
                }else{
                    System.out.println("test");
                    event.getChannel().sendMessage("Enter either !Apod, or !Apod <date>. Type !help for more information.");
                }

            }


        }
    }


    public String apod(String date) {
        Mono<ApodWrapper> apodWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("api_key", apiKey)
                        .queryParam("date", date)
                        .build())
                .retrieve()
                .bodyToMono(ApodWrapper.class);
       // apodWrapperMono.defaultIfEmpty(new ApodWrapper("This date is not available."));
        ApodWrapper aw = apodWrapperMono.block();
        String message = aw.getUrl();
        //send the message
        return message;
    }
    public String apod() {
        Mono<ApodWrapper> apodWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(ApodWrapper.class);

        String message = apodWrapperMono.block().getUrl();
        //send the message
        return message;
    }

}