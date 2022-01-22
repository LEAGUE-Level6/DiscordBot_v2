package org.jointheleague.discord_bot;


import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Feature1 extends Feature{
	public final String COMMAND = "!feature1";

    public Feature1(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Allows you to get a random number between 0 and 1000)");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
        	event.getChannel().sendMessage("Sending a message to the channel");
        }
    }

}
