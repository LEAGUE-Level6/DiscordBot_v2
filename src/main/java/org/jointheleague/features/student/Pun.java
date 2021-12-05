package org.jointheleague.features.student;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Pun extends Feature {
    public final String PUN = "!pun";

    public Pun(String channelName) {
        super(channelName);

        helpEmbed = new HelpEmbed(
                PUN,
                "type !pun for a pun"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(PUN)) {
            event.getChannel().sendMessage("When crushed, grapes let out a little wine.");
        }
    }
}
