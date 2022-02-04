package org.jointheleague.features.student.third_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class ReactionGif extends Feature {

    public final String COMMAND = "!gif";

    public ReactionGif(String channelName) {
        super(channelName);
        //what shows with !help
        helpEmbed = new HelpEmbed(COMMAND, "Need a gif for a certain occasion? Use " + COMMAND + " plus anything for the perfect reaction!");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent().toLowerCase();
    }

}
