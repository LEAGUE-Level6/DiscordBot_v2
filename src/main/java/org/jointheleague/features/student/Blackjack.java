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
    final int ANOTHER_ROUND = 4;
    int gameState = NOT_STARTED;

    private long availableMoney;

    private long bet;

    private int playerTotal = 0;

    private int dealerTotal = 0;

    private ArrayList<Card> deck = new ArrayList<Card>();


    private WebClient newDeckClient;

    private ArrayList<Card> dealerHand;
    private ArrayList<Card> playerHand;

    private String dealerHandString;
    private String playerHandString;

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
                newDeck();
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
                playerTotal = 0;
                for (int i = 0; i < playerHand.size(); i++){
                    playerTotal = playerTotal + value2int(playerHand.get(i).getValue());
                }
                if (playerTotal > 21){
                    event.getChannel().sendMessage("Bust!!!");
                    playerLost(event);
                }else {
                    event.getChannel().sendMessage("[H]it or [S]tand?");
                }
            }
            else if (messageContent.equalsIgnoreCase("s")){
                dealerTotal = 0;
                dealerHandString = "The dealer has a " + dealerHand.get(0).getSuit() + " of " + dealerHand.get(0).getValue() + " and " + dealerHand.get(1).getSuit() + " of " + dealerHand.get(1).getValue();
                for (int i = 0; i < dealerHand.size(); i++){
                    dealerTotal = value2int(dealerHand.get(i).getValue()) + dealerTotal;
                }

                event.getChannel().sendMessage(dealerHandString);

                while (dealerTotal < 17){
                    event.getChannel().sendMessage("The Dealer Hits.");
                    drawDealerCard();
                    dealerTotal = dealerTotal + value2int(dealerHand.get(dealerHand.size()-1).getValue());
                    dealerHandString = dealerHandString + "\nand " + dealerHand.get(dealerHand.size()-1).getSuit() + " of " + dealerHand.get(dealerHand.size()-1).getValue();
                    event.getChannel().sendMessage(dealerHandString);
                }

                if (dealerTotal>21){
                    event.getChannel().sendMessage("The Dealer Busts!");
                    playerWon(event);
                }else{
                    event.getChannel().sendMessage(playerHandString);
                    if (playerTotal > dealerTotal){
                        playerWon(event);
                    }else{
                        playerLost(event);
                    }
                }
            }
        }else if (gameState == ANOTHER_ROUND){
            if (messageContent.equalsIgnoreCase("y")){
                event.getChannel().sendMessage("Good Choice!");
                event.getChannel().sendMessage("How much are you betting?");
                event.getChannel().sendMessage("*I'll wait if you put down more than you have, or if you don't bet anything.");
                gameState = BETTING;
            }else if (messageContent.equalsIgnoreCase("n")){
                event.getChannel().sendMessage("Alright. You're leaving with $" + availableMoney + ". Have a great day!");
                gameState = NOT_STARTED;
            }
        }

        if(gameState != NOT_STARTED){
            if (messageContent.equals("Quit Blackjack Now")){
                event.getChannel().sendMessage("Alright! Thank's for playing! Hopefully we see you again soon!");
                gameState = NOT_STARTED;
            }
        }
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
        playerHand.add(cards.getCards().get(0));

        playerHandString = playerHandString + "\nand " + cards.getCards().get(0).getValue() + " of " + cards.getCards().get(0).getSuit();
    }

    public void drawDealerCard(){
        dealerHand.add(cards.getCards().get(0));

        dealerHandString = dealerHandString + "\nand " + cards.getCards().get(0).getValue() + " of " + cards.getCards().get(0).getSuit();
    }

    public void newDeck(){
        Mono<BlackjackWrapper> stringMono = newDeckClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .build())
                .retrieve()
                .bodyToMono(BlackjackWrapper.class);

        BlackjackWrapper deckBlock = stringMono.block();

        WebClient drawCardsClient = WebClient
                .builder()
                .baseUrl("https://deckofcardsapi.com/api/deck/"+deckBlock.getDeckId()+"/draw/?count=52")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        Mono<DrawCardsWrapper> cardsMono = drawCardsClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .build())
                .retrieve()
                .bodyToMono(DrawCardsWrapper.class);

        DrawCardsWrapper cards = cardsMono.block();

        deck = new ArrayList<Card>();
        for (int i = 0; i < 52; i++){
            deck.add(cards.getCards().get(i));
        }
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

    public void playerLost(MessageCreateEvent event){
        event.getChannel().sendMessage("The Dealer Won. :(");
        event.getChannel().sendMessage("You just lost $" + bet);
        availableMoney = availableMoney - bet;
        if (availableMoney == 0){
            event.getChannel().sendMessage("Looks like you're broke! Thanks for playing!");
            gameState = NOT_STARTED;
        }else{
            event.getChannel().sendMessage("You have only $" + availableMoney + " left!");
            event.getChannel().sendMessage("Play another round? [Y]es or [N]o");
            gameState = ANOTHER_ROUND;
        }
    }

    public void playerWon(MessageCreateEvent event){
        event.getChannel().sendMessage("You won!");
        availableMoney = availableMoney + bet;
        event.getChannel().sendMessage("You gained $" + bet + ", so you now have $" + availableMoney);
        event.getChannel().sendMessage("Play another round? [Y]es or [N]o");
        gameState = ANOTHER_ROUND;
    }
}
