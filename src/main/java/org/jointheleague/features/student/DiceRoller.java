package org.jointheleague.features.student;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class DiceRoller extends Feature{
    public final String COMMAND = "!roll";

    public DiceRoller(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Rolls dice of your choosing\nFormated as \"!roll #d#\"\nThe first number refers to the amount of dice while the second refers to the amount of sides on the dice (eg. !roll 2d6).");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
        	String[] message = messageContent.split(" ");
        	int amount = 0;
        	int sides = 0;
        	try {
        		//Checks the number of dice being rolled
            	String diceAmount = message[1].substring(0, message[1].indexOf("d"));
            	amount = Integer.parseInt(diceAmount);
    				
            	//Checks the amount of sides on each dice
            	String diceSides = message[1].substring(message[1].indexOf("d")+1);
            	sides = Integer.parseInt(diceSides);
            	int total = 0;
            	
                Random r = new Random();
                for (int i = 0; i < amount; i++) {
    				total += r.nextInt(sides);
    			}
                event.getChannel().sendMessage("You rolled a total of " + total);
			} catch (Exception e) {
				event.getChannel().sendMessage("Command not formated correctly");
			}
        	
        }
    }
}
