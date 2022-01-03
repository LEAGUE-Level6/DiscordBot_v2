package org.jointheleague.features.student;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class ReverseText extends Feature {

    public final String COMMAND = "!reverse";

    public ReverseText(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Reverses a given string that is typed after the command."
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        messageContent = messageContent.trim();
        
        if(messageContent.contains(COMMAND)) {
        	messageContent = messageContent.substring(messageContent.indexOf(COMMAND) + COMMAND.length()).trim();
        	
        	if(messageContent.isEmpty()) {
        		event.getChannel().sendMessage("Be sure to enter a string after the command.");
        	} else {
        		String reversedMessage = "";
        		
        		for(int i = messageContent.length() - 1; i >= 0; i--) {
        			reversedMessage += messageContent.charAt(i);
        		}
        		
        		event.getChannel().sendMessage(reversedMessage);
        	}
        }
    }

}
