package org.jointheleague.features.student;

import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.student.blackjack_wrapper.BlackjackWrapper;
import org.jointheleague.features.student.weatherwrapper.WeatherWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Blackjack extends Feature {

    public final String COMMAND = "!blackjack";

    final int NOT_STARTED = 0;
    final int INIT_MONEY = 1;
    final int BETTING = 2;
    final int HIT_STAND = 3;
    final int DRAWING = 4;
    int gameState = NOT_STARTED;

    public long availableMoney = 0;


    private WebClient newDeckClient;

    String newDeckUrl = "https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1";

    public Blackjack(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "A popular and addictive card game that causes many to lose money"
        );

        this.newDeckClient = WebClient
                .builder()
                .baseUrl(newDeckUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            if (gameState == NOT_STARTED) {
                //respond to message here
                event.getChannel().sendMessage("Welcome to blackjack!");
                event.getChannel().sendMessage("How much money are you using in dollars? (Change is not allowed)");
                event.getChannel().sendMessage("*And if you give me anything other that an integer, I'll just wait.");
                gameState = INIT_MONEY;
            }else{
                event.getChannel().sendMessage("Sorry, you're already in a game!");
            }
        }else if (gameState == INIT_MONEY){
            if (isNum(messageContent)) {
                availableMoney = Long.parseLong(messageContent);
                event.getChannel().sendMessage("Great! You are now using $" + availableMoney + "! Let's get started!");
                event.getChannel().sendMessage(newDeck());
                gameState = HIT_STAND;
            }
        }

        if(gameState != NOT_STARTED){
            if (messageContent.equals("Quit Blackjack Now")){
                event.getChannel().sendMessage("Alright! Thank's for playing! Hopefully we see you again soon!");
                gameState = NOT_STARTED;
            }
        }
    }

    public String response(MessageCreateEvent event){
        String lastMessage = event.getMessageContent();
        String newMessage = lastMessage;
        while (newMessage == lastMessage){
            newMessage = event.getMessageContent();
        }
        return newMessage;
    }

    public boolean isNum(String message){
        try{
            Long.parseLong(message);
        }catch (Exception e){
            return false;
        }

        return true;
    }

    public String drawCards(){
        String deckID = newDeck();
        return deckID;
    }

    public String newDeck(){
        Mono<BlackjackWrapper> stringMono = newDeckClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .build())
                .retrieve()
                .bodyToMono(BlackjackWrapper.class);

        BlackjackWrapper deck = stringMono.block();

        return deck.getDeckId();
    }
}
