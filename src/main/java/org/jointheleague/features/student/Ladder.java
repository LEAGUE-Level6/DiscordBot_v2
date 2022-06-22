package org.jointheleague.features.student;

import java.time.Instant;
import java.util.stream.Stream;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Ladder extends Feature {

    public final String COMMAND = "!ladder";

    public Ladder(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Keeps track of the total number of times this command has been called"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            Stream<Message> m = event.getChannel().getMessagesAroundAsStream(event.getMessage());
            
            Object arr[] = m.toArray();
            int max = 0;
            for(int i = 0; i < arr.length; i++) {
            	if(((Message) arr[i]).getReadableContent().toString().startsWith("The ladder is now") && ((Message) arr[i]).getAuthor().getName().equals("BotBot")){
            		int num = Integer.parseInt(((Message) arr[i]).getReadableContent().toString().split(" ")[4]);
            		if(num > max) {
            			max = num;
            			break;
            		}
            	}
            }
            event.getChannel().sendMessage("The ladder is now " + (max + 1) + " users high!");
            
        }
    }

}
