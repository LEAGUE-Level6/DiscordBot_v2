package org.jointheleague.features.Derek;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.examples.first_features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.ArrayList;
import java.util.Random;

public class Praise extends Feature {

    public final String COMMAND = "!praise";

    public Praise(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "I thought the name says it all. It gives praise. That's it. Stop asking"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            ArrayList<String> list = new ArrayList<String>();
            list.add("You are amazing!");
            list.add("Don't let anything get in your way!");
            list.add("It doesn't matter what they call you!");
            list.add("Placeholder");
            list.add("Stop asking for praise!");
            Random r = new Random();
            String message = list.get(r.nextInt(list.size()));
            event.getChannel().sendMessage(message);
        }
    }

}
