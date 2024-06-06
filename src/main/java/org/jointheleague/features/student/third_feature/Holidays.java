package org.jointheleague.features.student.third_feature;
import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.cat_facts_api.CatWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.util.List;


public class Holidays extends Feature {

    public final String COMMAND = "!holidays";
    private static final String baseUrl = "https://holidays.abstractapi.com/v1/";
    private final String apiKey = "736d5028a46c42ada682689ef387e002";
    private WebClient webClient;
    String month = "";
    String day = "";

    public Holidays(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "A command to get holidays on any date. Use !holidays M/D. Eg. !holidays 12/25");

        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }


    public String getHoliday() {
        try {
            Mono<List<HolidaysWrapper>> holidaysWrapperMono = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("api_key", apiKey)
                            .queryParam("country", "US")
                            .queryParam("year", "2024")
                            .queryParam("month", month)
                            .queryParam("day", day)
                            .build())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<HolidaysWrapper>>() {});

            List<HolidaysWrapper> holidaysWrapper = holidaysWrapperMono.block();

            String message = holidaysWrapper.get(0).getName();

            return message;
        }
        catch(Exception e){
            e.printStackTrace();

        }
        return null;
    }




    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();

        System.out.println(messageContent);
        String dateMessage = messageContent.replaceAll(" ", "").replace(COMMAND, "");
        String[] dates = dateMessage.split("/");
        System.out.println(dateMessage);

        for(int i = 0; i<dates.length;i++){
            System.out.println(dates[i]);
        }
        month = dates[0];
        day = dates[1];


        String holiday = getHoliday();
        System.out.println(holiday);

        if (messageContent.contains(COMMAND)) {
            event.getChannel().sendMessage(holiday);
            if(holiday==null){
                event.getChannel().sendMessage("There are no holidays on this date.");
            }


        }
    }



}





