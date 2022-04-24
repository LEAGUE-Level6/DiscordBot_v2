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
    }
}
