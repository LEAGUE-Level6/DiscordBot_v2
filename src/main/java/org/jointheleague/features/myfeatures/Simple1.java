package org.jointheleague.features.myfeatures;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Simple1 extends Feature {

    public final String COMMAND = "!greeting";

    public Simple1(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Greets you");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            event.getChannel().sendMessage("Hello!");
        }
    }

}
