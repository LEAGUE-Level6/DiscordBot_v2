package org.jointheleague.features.examples.second_features;

import static org.junit.Assert.assertArrayEquals;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class AdventureGame extends Feature{
	public final String COMMAND = "!playGame";
	
	int[] levels = new int[20];
	
    public AdventureGame(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Fun Adventure Game about Bobster's Revenge!");
        for  (int i = 0; i < levels.length; i++) {
        	levels[i] = 0;
        }
        levels[0] = 1;
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
        	if (levels[0] == 1) {
        		event.getChannel().sendMessage("Would you like to go through the right or left door?");
        		levels[0] = 0;
        		levels[1] = 1;
        	}
        }
        else if (messageContent.startsWith("!choose")){
        	if (levels[1] == 1) {
        		if (messageContent.contains("left")) {
        			levels[2] = 1;
        			levels[1] = 0;
        			
        		}
        		else if (messageContent.contains("right")) {
        			levels[3] = 1;
        			levels[1] = 0;
        		}
        	}
        }
    }

}
