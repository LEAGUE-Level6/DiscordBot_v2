package org.jointheleague.features.student;

import java.time.Instant;
import java.util.stream.Stream;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class RecentCreation extends Feature {

    public final String COMMAND = "!delete";

    public RecentCreation(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Returns all the users that were created within the past week, using !recent."
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            Stream<Message> m = event.getChannel().getMessagesAroundAsStream(event.getMessage());
            
            Object arr[] = m.toArray();
            event.getChannel().sendMessage("$Retrieved " + arr.length + " messages");

            int lim = 5;
            for(int i = 0; i < lim; i++) {
            	Message current = (Message) arr[i];
            	if(current.getReadableContent().startsWith("$") || current.getReadableContent().startsWith("!")){
            		lim++;
            		continue;
            	}
            	event.getChannel().sendMessage("$" + i + " Messages ago: " + current.getReadableContent());
            }
            
        }
    }

}
