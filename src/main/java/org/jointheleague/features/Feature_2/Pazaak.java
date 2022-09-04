package org.jointheleague.features.Feature_2;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.ArrayList;
import java.util.Random;

public class Pazaak extends Feature {

    public final String COMMAND = "!Pazaak";
    public ArrayList<Integer> sideDeck;
    public ArrayList<Integer> mainDeck;
    public ArrayList<Integer> playerHand;
    public boolean playerStand = false;
    public boolean botStand = false;
    public int cardIndex = 0;
    public Pazaak(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Pazaak is a popular card game from the days of the Old Republic. The goal" +
                        "is to come closest to 20 without going over, and the player with the highest number wins the set." +
                        "A match is made of 3 sets. Each turn, you draw a card, which is added to your total. However, there is a twist. " +
                        "You will have a side deck of 10 cards that you can play after you draw. Keep in mind that you can only play" +
                        "1 card from your side deck per turn. These cards will add or subtract from your total." +
                        "You will have 1, -1, 2, -2, 3, -3, 4, -4, 5, and -5." +
                        "Alright, I think that's all," +
                        "may the force be with you."
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //set up decks, shuffle deck
            event.getChannel().sendMessage("Shuffling the deck . . .");
            setUpDecks();
            event.getChannel().sendMessage("Decks shuffled! Have fun, play fair, and may the force be with you!");
            //Play the game
            int turnCt = 1;
            int playerTot = 0;
            int botTot = 0;
            int playerWins = 0;
            int botWins = 0;
            //Deal hand; this stays the same throughout the match
            dealHand();
            //Display player hand
            event.getChannel().sendMessage("Your hand: " + playerHand.get(0) + " " +
                    playerHand.get(1) + " " + playerHand.get(2) + " " + playerHand.get(3) + " ");
            while(playerWins < 3 || botWins < 3) {
                //Turn sequence:

               //PLAYER TURN:
                if (turnCt == 1) {
                    event.getChannel().sendMessage("Your turn!");
                    //Deal a card to the player
                    playerTot += mainDeck.get(cardIndex);
                    event.getChannel().sendMessage("Your card: " + mainDeck.get(cardIndex));
                    event.getChannel().sendMessage("Your total: " + playerTot);
                    cardIndex++;
                    //Ask if they want to play a card ("N" or "Y card value)
                    event.getChannel().sendMessage("Do you want to play a card? Type only the card value you want to play" +
                            "eg: '10' or '-2', otherwise you won't be playing anything");
                    if (messageContent.startsWith("N")) {
                        continue;
                    } else {
                        try {
                            int playerInput = Integer.parseInt(messageContent);
                            boolean isValid = false;
                            //check if the input is a card in the player's hand
                            for (int i = 0; i < playerHand.size(); i++) {
                                if (playerInput == playerHand.get(i)) {
                                    isValid = true;
                                    //remove card from their hand
                                    playerHand.remove(i);
                                    break;
                                }
                            }
                            if (isValid) {
                                //Add their card to their total
                                playerTot += playerInput;
                                event.getChannel().sendMessage("The card you played: " + playerInput);
                                event.getChannel().sendMessage("Your total: " + playerTot);
                            } else {
                                event.getChannel().sendMessage("Play a card in your hand, idiot");
                            }
                        } catch (Exception e) {
                            event.getChannel().sendMessage("Enter either 'N' or the value of the card you want to play");
                        }
                    }
                    //Ask if they want to stand
                    event.getChannel().sendMessage("Do you want to stand? (type 'y' or 'n')");
                    if (event.getMessageContent().startsWith("y")) {
                        playerStand = true;
                        event.getChannel().sendMessage("Successfully stand-ed :)");
                    } else if (event.getMessageContent().startsWith("n")) {
                        event.getChannel().sendMessage("Successfully not stand-ed!");
                    } else {
                        event.getChannel().sendMessage("Enter y or n, you Gungan. I'm just going to assume you don't want to stand.");
                    }
                    turnCt *= -1;
                }

                //BOT TURN:
                if(turnCt == -1){
                    event.getChannel().sendMessage("My turn!");
                    //Pretty easy, just deal a card to their total
                    botTot += mainDeck.get(cardIndex);
                    event.getChannel().sendMessage("My total: " + botTot);
                    //randomly determine if they will use a card from their side deck (the bot is actually cheating :O )
                    Random r = new Random();
                    int x = r.nextInt(5);
                    if(x == 1){
                        int card = r.nextInt(6);
                        botTot += card;
                        event.getChannel().sendMessage("My total: " + botTot);
                    }
                    //use logic to determine if the bot should stand
                }
            }
        }
    }

    public void setUpDecks() {
        //set up side decks
        sideDeck = new ArrayList<Integer>();
        for (int i = 1; i < 6; i++) {
            sideDeck.add(i);
            sideDeck.add(-i);
        }
        //set up and shuffle main deck
        mainDeck = new ArrayList<Integer>();
        for(int i = 0; i < 10; i++){
            //main deck should have 40 cards, 4 copies of each number from 1-10
            mainDeck.add(i + 1);
            mainDeck.add(i + 1);
            mainDeck.add(i + 1);
            mainDeck.add(i + 1);
        }
        //shuffle main deck
        int shuffleTimes = 10000;
        for(int i = 0; i < shuffleTimes; i++){
            //get random indexes in the deck
            Random r = new Random();
            int n1 = r.nextInt(40);
            int n2 = r.nextInt(40);
            int parser = mainDeck.get(n1);
            //swap the indexes
            mainDeck.set(n1, n2);
            mainDeck.set(n2, parser);
        }
        //shuffle side decks
        for(int i = 0; i < shuffleTimes; i++){
            //get random indexes in the deck
            Random r = new Random();
            int n1 = r.nextInt(10);
            int n2 = r.nextInt(10);
            int parser = sideDeck.get(n1);
            //swap the indexes
            sideDeck.set(n1, n2);
            sideDeck.set(n2, parser);
        }
    }
    public void dealHand(){
        //Create arraylist of 4 cards
        playerHand = new ArrayList<Integer>();
        for(int i = 0; i < 4; i++){
            playerHand.add(sideDeck.get(i));
        }
    }
    public void PlayerTurn(){
        //deal a card
    }
    public void EnemyTurn(){

    }
}
