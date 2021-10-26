package org.jointheleague.features.sameerbot.third;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.Client;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Yee extends Feature {

    public final String COMMAND = "!yee";

    public Yee(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "YEEEEEE!!!!!!"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            event.getChannel().sendMessage("https://tenor.com/view/yee-yeedinasour-dinasour-gif-4930781");
            event.getChannel().sendMessage("https://youtu.be/q6EoRBvdVPQ");
        }
    }
}