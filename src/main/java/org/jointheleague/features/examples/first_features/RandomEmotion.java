package org.jointheleague.features.examples.first_features;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class RandomEmotion extends Feature{
	
	public final String COMMAND = "!emotion";

	public RandomEmotion(String channelName) {
		super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Allows you to get a random number between 0 and 1000)");

	}

	@Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            Random r = new Random();
            int emo=r.nextInt(5);
            if(emo==0) {
            	event.getChannel().sendMessage(":)");
            	event.getChannel().sendMessage("Happy!");
            }
            else if(emo==1) {
            	event.getChannel().sendMessage(";)");
            	event.getChannel().sendMessage("Very Happy!!!");
            }
            else if(emo==2) {
            	event.getChannel().sendMessage(":p");
            	event.getChannel().sendMessage("Pretty Happy");
            }
            else if(emo==3) {
            	event.getChannel().sendMessage(":|");
            	event.getChannel().sendMessage("Ehhhhh");
            }
            else {
            	event.getChannel().sendMessage(":(");
            	event.getChannel().sendMessage("Sad...");
            }
            
        }
    }

}
