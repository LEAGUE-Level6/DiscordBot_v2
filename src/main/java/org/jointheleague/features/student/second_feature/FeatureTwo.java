package org.jointheleague.features.student.second_feature;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;

public class FeatureTwo extends FeatureTemplate{
	 public final String COMMAND = "!blackjack";
	    private final Random random = new Random();
	    int numberToGuess;

	    public FeatureTwo(String channelName) {
	        super(channelName);
	        helpEmbed = new HelpEmbed(COMMAND, "Get as close to 21 as you can with your cards. 'Hit' means to ask for another card, stand means you are finished. Between us, whoever is closer to 21 wins!");
	    }

	    @Override
	    public void handle(MessageCreateEvent event){
	        String messageContent = event.getMessageContent();

	        //start the game with the command
	        if (messageContent.equals(COMMAND)) { 
	        	String botCard = cardPicker();
	        	String yourCard = cardPicker();
	            event.getChannel().sendMessage("My Card: " + botCard + "\n Your Card: " + yourCard + "\n !Hit or !Stand ?");
	        }
	        
	        if(messageContent.equals("!Hit")) {
	        	
	        } else if(messageContent.equals("!Stand")) {
	        	
	        }
	        
	       
	    }
	    public String cardPicker() {
	    	String cardName = "";
        	numberToGuess = random.nextInt(10) + 2;
            if(numberToGuess == 10) {
            	numberToGuess = random.nextInt(4);
            	if(numberToGuess == 0) {
            		cardName+="10";
            	} else if(numberToGuess == 1) {
            		cardName+="Jack";
            	} else if(numberToGuess == 2) {
            		cardName+="Queen";
            	} else if(numberToGuess == 3) {
            		cardName+="King";
            	}
            } else if(numberToGuess == 11) {
            	cardName+="Ace";
            } else {
            	cardName+=(numberToGuess);
            }
            numberToGuess = random.nextInt(4);
            if(numberToGuess == 0) {
            	cardName+=" of Hearts";
            } else if(numberToGuess == 1) {
            	cardName+=" of Diamonds";
            } else if(numberToGuess == 2) {
            	cardName+=" of Spades";
            } else if(numberToGuess == 3) {
            	cardName+=" of Clubs";
            }
            return cardName;
	    }
}
