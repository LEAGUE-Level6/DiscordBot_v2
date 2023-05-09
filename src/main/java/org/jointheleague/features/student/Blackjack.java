package org.jointheleague.features.student;

import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.student.blackjack_wrapper.BlackjackWrapper;
import org.jointheleague.features.student.blackjack_wrapper.Card;
import org.jointheleague.features.student.blackjack_wrapper.DrawCardsWrapper;
import org.jointheleague.features.student.weatherwrapper.WeatherWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Blackjack extends Feature {

    public final String COMMAND = "!blackjack";

    final int NOT_STARTED = 0;
    final int INIT_MONEY = 1;
    final int BETTING = 2;
    final int HIT_STAND = 3;
    final int DRAWING = 4;
    int gameState = NOT_STARTED;

    private long availableMoney;

    private long bet;


    private WebClient newDeckClient;
    private WebClient drawCardsClient;
    private WebClient drawCardClient;

    private ArrayList<Card> dealerHand;
    private ArrayList<Card> playerHand;

    private String dealerHandString;
    private String playerHandString;

    String newDeckUrl = "https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1";

    String deckID;

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
                deckID = newDeck();
                event.getChannel().sendMessage("How much are you betting?");
                event.getChannel().sendMessage("*I'll wait if you put down more than you have, or if you don't bet anything.");
                gameState = BETTING;
            }
        }else if (gameState == BETTING){
            if (isNum(messageContent) && Long.parseLong(messageContent) <= availableMoney && Long.parseLong(messageContent) > 0){
                event.getChannel().sendMessage("Great!  $" + messageContent + " it is!");
                bet = Long.parseLong(messageContent);
                drawCards();
                event.getChannel().sendMessage(dealerHandString);
                event.getChannel().sendMessage(playerHandString);
                event.getChannel().sendMessage("[H]it or [S]tand?");
                gameState = HIT_STAND;
            }
        }else if (gameState == HIT_STAND){
            if (messageContent.equalsIgnoreCase("h")){
                drawPlayerCard();
                event.getChannel().sendMessage(dealerHandString);
                event.getChannel().sendMessage(playerHandString);
                int total = 0;
                for (int i = 0; i < playerHand.size(); i++){
                    total = total + value2int(playerHand.get(i).getValue());
                }
                event.getChannel().sendMessage("[H]it or [S]tand?");
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

    public void drawCards(){
        Mono<DrawCardsWrapper> stringMono = drawCardsClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .build())
                .retrieve()
                .bodyToMono(DrawCardsWrapper.class);

        DrawCardsWrapper cards = stringMono.block();

        dealerHand = new ArrayList<Card>();
        dealerHand.add(cards.getCards().get(0));
        dealerHand.add(cards.getCards().get(1));

        playerHand = new ArrayList<Card>();
        playerHand.add(cards.getCards().get(2));
        playerHand.add(cards.getCards().get(3));

        dealerHandString = "The dealer is showing a " + dealerHand.get(0).getValue() + " of " + dealerHand.get(0).getSuit();
        playerHandString = "You have a " + playerHand.get(0).getValue() + " of " + playerHand.get(0).getSuit() + " and " + playerHand.get(1).getValue() + " of " + playerHand.get(1).getSuit();
    }

    public void drawPlayerCard(){
        Mono<DrawCardsWrapper> stringMono = drawCardClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .build())
                .retrieve()
                .bodyToMono(DrawCardsWrapper.class);

        DrawCardsWrapper cards = stringMono.block();

        playerHand.add(cards.getCards().get(0));

        playerHandString = playerHandString + "\nand " + cards.getCards().get(0).getValue() + " of " + cards.getCards().get(0).getSuit();
    }

    public String newDeck(){
        Mono<BlackjackWrapper> stringMono = newDeckClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .build())
                .retrieve()
                .bodyToMono(BlackjackWrapper.class);

        BlackjackWrapper deck = stringMono.block();

        drawCardsClient = WebClient
                .builder()
                .baseUrl("https://deckofcardsapi.com/api/deck/"+deck.getDeckId()+"/draw/?count=4")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        drawCardClient = WebClient
                .builder()
                .baseUrl("https://deckofcardsapi.com/api/deck/"+deck.getDeckId()+"/draw/?count=1")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return deck.getDeckId();
    }

    public int value2int(String value){
        switch (value){
            case "ACE":
                return 1;
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            case "8":
                return 8;
            case "9":
                return 9;
            case "10":
                return 10;
            case "KING":
                return 10;
            case "QUEEN":
                return 10;
            case "JACK":
                return 10;
        }
        return 0;
    }
}
