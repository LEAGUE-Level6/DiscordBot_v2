package org.jointheleague.features.student;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PingPongListener extends Feature {

    public final String COMMAND = "!ping";

    public PingPongListener(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Pongs your ping!");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            event.getChannel().sendMessage("pong");
        }
    }
}
