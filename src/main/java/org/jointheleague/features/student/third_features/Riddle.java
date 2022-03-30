package org.jointheleague.features.student.third_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Random;

public class Riddle extends Feature {

    public final String COMMAND = "!riddle";
    private final Random random = new Random();
    private final String answer = "egg";
    String[] answers = { "egg", "candle", "all of them" };
    String[] questions = { "What has to be broken before you can use it?", " I’m tall when I’m young, and I’m short when I’m old. What am I?", "What month of the year has 28 days?" };
    String COMMAND2;
    int ran;
    private static final String baseUrl = "https://www.riddle.com/api/v2";

    private WebClient webClient;

    public void randomCreater(){
        ran = random.nextInt(3);
        COMMAND2 = "!riddle " + answers[ran];
    }

    public Riddle(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Riddle game. You will be given a riddle, your goal is to get the answer.");
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    public void testRequest() {
        Mono<String> stringMono = webClient
                .get()
                .retrieve()
                .bodyToMono(String.class);


        String response = stringMono.block();

        System.out.println(response);
    }

    public Riddle getRiddle() {

        Mono<Riddle> riddleMono = webClient
                .get()
                .retrieve()
                .bodyToMono(Riddle.class);

        Riddle response = riddleMono.block();
        System.out.println(response);
        return response;


    }

    @Override
    public void handle(MessageCreateEvent event){
        String messageContent = event.getMessageContent();

        //start the game with the command
        if (messageContent.equalsIgnoreCase((COMMAND))){
            randomCreater();
            event.getChannel().sendMessage(questions[ran]);
        }
        //check a guess
        if (messageContent.equalsIgnoreCase(COMMAND2)) {
            event.getChannel().sendMessage("Correct! The answer was " + answers[ran] + ".");

        }
    }
}
