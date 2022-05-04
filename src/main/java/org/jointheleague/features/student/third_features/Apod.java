package org.jointheleague.features.student.third_features;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.student.third_features.plain_old_java_objects.ApodApi.ApodWrapper;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import javax.xml.ws.handler.MessageContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//A swagger page for this very simple API can be found here: https://app.swaggerhub.com/apis-docs/whiterabbit8/meowfacts/1.0.0
public class Apod extends Feature {

    private String date = java.time.LocalDate.now().toString();
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
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date parsedDate = formatter.parse(stringSplit[1]);
                    Date lastDate = formatter.parse("1995-06-20");
                    Date todayDate = formatter.parse(date);
                   if (parsedDate.before(lastDate) || parsedDate.after(todayDate)){
                       event.getChannel().sendMessage("There is no image for that date, true using a date that is after 1995-06-20, and before today.");
                   }
                } catch (ParseException e) {
                    e.printStackTrace();
                    event.getChannel().sendMessage("Enter either !Apod, or !Apod <date>. Type !help for more information.");
                }
                String date = stringSplit[1];
                if (date.contains("-")){
                    String picture = apod(date);
                    event.getChannel().sendMessage(picture);
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