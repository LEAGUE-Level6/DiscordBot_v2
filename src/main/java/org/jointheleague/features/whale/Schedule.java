package org.jointheleague.features.whale;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Schedule extends Feature {

    public final String COMMAND = "!addEvent";

    public Schedule(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Adds an event. Start with the event name and time (<Name> 9:30pdt)"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
        	String eventString = messageContent.substring(COMMAND.length());
        	
            event.getChannel().sendMessage("Sending a message to the channel");
        }
    }

}
