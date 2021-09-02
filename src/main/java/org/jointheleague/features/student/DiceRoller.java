package org.jointheleague.features.student;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class DiceRoller extends Feature{
    public final String COMMAND = "!roll";

    public DiceRoller(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Rolls a die of your choosing.");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
        	String[] message = messageContent.split(" ");
        	String diceSides = message[1].substring(message[1].indexOf("d")+1);
        	int sides = Integer.parseInt(diceSides);
        	String diceAmount = message[1].substring(0, message[1].indexOf("d"));
        	int amount = Integer.parseInt(diceAmount);
        	
        	int total = 0;
        	
            Random r = new Random();
            for (int i = 0; i < amount; i++) {
				total += r.nextInt(sides);
			}
            event.getChannel().sendMessage("You rolled a total of " + total);
        }
    }
}
