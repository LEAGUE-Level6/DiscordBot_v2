package org.jointheleague.features.templates;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Locale;

public class FeatureTemplate extends Feature {

    public final String COMMAND = "q!command";

    public FeatureTemplate(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Give a brief description of your feature here, including how the user interacts with it"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent().toLowerCase(Locale.ROOT);
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            event.getChannel().sendMessage("Sending a message to the channel");
        }
    }

}
