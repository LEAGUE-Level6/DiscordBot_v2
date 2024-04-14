package org.jointheleague.features.student.third_feature;
import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.cat_facts_api.CatWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;



public class Holidays extends Feature {

    public final String COMMAND = "!holidays";
    private static final String baseUrl = "https://holidays.abstractapi.com/v1/";
    private final String apiKey = "736d5028a46c42ada682689ef387e002";
    private WebClient webClient;

    public Holidays(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Trivia questions for the user to answer. Start the game with !trivia ask and enter a guess using !trivia 'your guess'. E.g. !trivia b. To end the game, use !trivia end. To view your score, use !trivia score."
        );

        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }


    public String getHoliday() {
        System.out.println("1");
        Mono<HolidaysWrapper> holidaysWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("api_key", apiKey)
                        .queryParam("country", "US")
                        .queryParam("year", "2021")
                        .queryParam("month", "01")
                        .queryParam("day", "01")
                        //.query("https://holidays.abstractapi.com/v1/api_key=736d5028a46c42ada682689ef387e002&country=US&year=2021&month=1&day=1")
                        .build())
                .retrieve()
                .bodyToMono(HolidaysWrapper.class);
        System.out.println("2");

        HolidaysWrapper holidaysWrapper =  holidaysWrapperMono.block();
        System.out.println("3");
        String message = holidaysWrapper.getName();
        System.out.println("4");
        return message;
    }




    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();

        System.out.println(messageContent);
        System.out.println(getHoliday());

        if (messageContent.equals(COMMAND)) {

            event.getChannel().sendMessage(getHoliday());


        }
    }



}





