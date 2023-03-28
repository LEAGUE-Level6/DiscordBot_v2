package org.jointheleague.features.examples.first_features;



import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class BadJoke extends Feature{
	public final String COMMAND = "!badJoke";

    public BadJoke(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Tells a bad joke");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            event.getChannel().sendMessage("The bad joke... is your face. (insert sarcastic laughter here)");
        }
    }

}
