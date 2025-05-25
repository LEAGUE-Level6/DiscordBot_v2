package org.jointheleague.features.student.first_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Greeter extends Feature{
	public final String COMMAND = "!greet";
	public Greeter(String channelName) {
	        super(channelName);
	        //Create a help embed to describe feature when !help command is sent
	        helpEmbed = new HelpEmbed(
	                COMMAND,
	                "Greets you."
	        );
	    }

	    @Override
	    public void handle(ReceivedMessage event) {
	        String messageContent = event.getMessageContent();
	        if (messageContent.startsWith(COMMAND)) {
	            //respond to message here
	            event.sendResponse("Hello! How can I help you?");
	        }
	    }
	}


