package org.jointheleague.features.myfeatures;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class UpdatedHighLow extends Feature {

    public final String COMMAND = "!game";

    public UpdatedHighLow(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Choose from 3 levels and play a guessing game!");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            event.getChannel().sendMessage("What level of difficulty will you choose? Easy, medium, or hard? !game easy  for easy level");
        }
        //easy, medium, vs hard difficulty
        //easy: 1-50, medium: 1-500, hard: -500-500
        //check numbers
    }

}

